// Robert Weischedel
// u0887905
// CS 4400 - Linking Lab

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <errno.h>
#include <elf.h>

/* Given the in-memory ELF header pointer as `ehdr` and a section
   header pointer as `shdr`, returns a pointer to the memory that
   contains the in-memory content of the section */
   //finds content of a section
#define AT_SEC(ehdr, shdr) ((void *)(ehdr) + (shdr)->sh_offset)

static void check_for_shared_object(Elf64_Ehdr *ehdr);
static void beginInspection(Elf64_Ehdr *ehdr);
static void inspectBytes(Elf64_Ehdr *ehdr, Elf64_Shdr *symTable, Elf64_Rela *relas, Elf64_Sym *syms, int relaCount, char *lines, unsigned long offset, int size);
static int determineBit(unsigned int byte, int bit);
static int findFunctionLength(Elf64_Ehdr *ehdr, unsigned int off);
static void printValues(Elf64_Rela *relas, Elf64_Sym *syms, char *lines, unsigned int off, int relaCount, int flag);
static void fail(char *reason, int err_code);

int main(int argc, char **argv) {
  int fd;
  size_t len;
  void *p;
  Elf64_Ehdr *ehdr;

  if (argc != 2)
    fail("expected one file on the command line", 0);

  /* Open the shared-library file */
  fd = open(argv[1], O_RDONLY);
  if (fd == -1)
    fail("could not open file", errno);

  /* Find out how big the file is: */
  len = lseek(fd, 0, SEEK_END);

  /* Map the whole file into memory: */
  p = mmap(NULL, len, PROT_READ, MAP_PRIVATE, fd, 0);
  if (p == (void*)-1)
    fail("mmap failed", errno);

  /* Since the ELF file starts with an ELF header, the in-memory image
     can be cast to a `Elf64_Ehdr *` to inspect it: */
  ehdr = (Elf64_Ehdr *)p;

  /* Check that we have the right kind of file: */
  check_for_shared_object(ehdr);

  /* Add a call to your work here */
  beginInspection(ehdr);
  return 0;
}

// Checks the first bytes to make sure it's an ELF file
static void check_for_shared_object(Elf64_Ehdr *ehdr) {
  if ((ehdr->e_ident[EI_MAG0] != ELFMAG0)
      || (ehdr->e_ident[EI_MAG1] != ELFMAG1)
      || (ehdr->e_ident[EI_MAG2] != ELFMAG2)
      || (ehdr->e_ident[EI_MAG3] != ELFMAG3))
    fail("not an ELF file", 0);

  if (ehdr->e_ident[EI_CLASS] != ELFCLASS64)
    fail("not a 64-bit ELF file", 0);

  if (ehdr->e_type != ET_DYN)
    fail("not a shared-object file", 0);
}


static Elf64_Shdr *sectionByIndex(Elf64_Ehdr *ehdr, int idx) {
  Elf64_Shdr *shdrs = ((void*)ehdr) + ehdr->e_shoff;
  return &shdrs[idx];
}


static Elf64_Shdr *sectionByName(Elf64_Ehdr *ehdr, char *name) {
  Elf64_Shdr *shdrs = ((void*)ehdr) + ehdr->e_shoff;
  char *lines = (void*)ehdr + shdrs[ehdr->e_shstrndx].sh_offset;
  int i = 0;
  for (i = 0; i < ehdr->e_shnum; i++) {
    if (strcmp((lines + shdrs[i].sh_name), name) == 0) {
      Elf64_Shdr *shdrs = ((void*)ehdr)+ehdr->e_shoff;
      return &shdrs[i];
    }
  }
  return NULL;
}

static void beginInspection(Elf64_Ehdr *ehdr) {
  Elf64_Shdr *relaTable = sectionByName(ehdr, ".rela.dyn");
  Elf64_Rela *relas = AT_SEC(ehdr, relaTable);
  int relaCount = (relaTable->sh_size / sizeof(Elf64_Rela));

  Elf64_Shdr *symTable = sectionByName(ehdr, ".dynsym");
  Elf64_Sym *syms = AT_SEC(ehdr, symTable);
  int symCount = (symTable->sh_size / sizeof(Elf64_Sym));

  char *strs = AT_SEC(ehdr, sectionByName(ehdr, ".dynstr"));

  int i;
  for(i = 0; i < symCount; i++) {
    if((ELF64_ST_TYPE(syms[i].st_info) == 2) && (syms[i].st_size != 0)) {
      printf("%s\n",strs + syms[i].st_name);
      Elf64_Shdr *shdr = sectionByIndex(ehdr, syms[i].st_shndx);
      unsigned long shdrOffset = syms[i].st_value;
      int size = shdr->sh_size;
      inspectBytes(ehdr, symTable, relas, syms, relaCount, strs, shdrOffset, size);
    }
  }
}

static void inspectBytes(Elf64_Ehdr *ehdr, Elf64_Shdr *symTable, Elf64_Rela *relas, Elf64_Sym *syms, int relaCount, char *lines, unsigned long off, int size) {

  int i;
  unsigned char* assembleAddr = ((void*)ehdr) + off;

  for (i = 0; i < size; i++) {
    unsigned char byteCheck = *(assembleAddr + i);
    switch (byteCheck) {
    case 235 : {
        unsigned int offset = off + i + 2 + *((char *)(assembleAddr + i + 1));
        int functionLength = findFunctionLength(ehdr, offset);
        inspectBytes(ehdr, symTable, relas, syms, relaCount, lines, offset, functionLength);
        break;
      }
    case 233 : {

        Elf64_Rela *relaPlt;
        int pltCount;

        Elf64_Shdr *shdrPlt = sectionByName(ehdr, ".rela.plt");

        int address = *((int *)(assembleAddr + i + 1));
        unsigned int pltOffset = off + i + 11 + address;
        unsigned char *pltLocation1 = ((void *)(ehdr)) + off + i + 5 + address;
        unsigned int pltLocation2 = *((unsigned int *)(pltLocation1 + 2)) + pltOffset;

        if (shdrPlt != NULL){
          relaPlt = AT_SEC(ehdr, shdrPlt);
          pltCount = (shdrPlt->sh_size / sizeof(Elf64_Rela));
          printValues(relaPlt, syms, lines, pltLocation2, pltCount, 1);
        }

        i += 4;

        break;
    }
    case 195:{
      return;
    }
    case 139 :
    case 137 :
    case 99 : {

      unsigned char b = *(assembleAddr + i + 1);

      int j;
      int bitTotal = 0;

      for (j = 0; j <= 2; j++) {
        if(determineBit(b, 2 - j) == 1){
          int sum = 1;
          int val = 2 - j;
          while(val > 0){
            sum *= 2;
            val--;
          }
          bitTotal += sum;
        }
      }

      if (determineBit(b, 6) == 1 && determineBit(b, 7) == 1) {
        i++;
        break;
      }
      else if ((determineBit(b, 6) == 1 && determineBit(b,7) == 1) || (bitTotal != 4 && bitTotal != 5)) {
        i++;
        break;
      }
      else if (determineBit(b, 6) == 0 && determineBit(b, 7) == 0 && bitTotal == 4) {
        i += 2;
        break;
      }
      else if (determineBit(b, 6) == 0 && determineBit(b, 7) == 0 && bitTotal == 5) {
        unsigned int offset = (unsigned int)6 + i + off + *((unsigned int *)(assembleAddr + i + 3));
        printValues(relas, syms, lines, offset, relaCount, 0);
        i += 5;
      }
    }
    case 72 : {

        unsigned char b1 = *(assembleAddr + i + 1);
        unsigned char b2 = *(assembleAddr + i + 2);

        int j;
        int bitTotal2 = 0;

        for (j = 0; j <= 2; j++) {
          if(determineBit(b2, 2 - j) == 1){
            int sum = 1;
            int val = 2 - j;
            while(val > 0){
              sum *= 2;
              val--;
            }
            bitTotal2 += sum;
          }
        }

        if (determineBit(b2, 6) == 1 && determineBit(b2, 7) == 1 && (b1 == 139 || b1 == 99 || b1 == 137)) {
          i += 2;
          break;
        }
        else if (((determineBit(b2, 6) == 1 && determineBit(b2, 7) == 1) || (bitTotal2 != 5 && bitTotal2 != 4)) && (b1 == 139 || b1 == 99 || b1 == 137)) {
          i += 2;
          break;
        }
        else if (determineBit(b2, 6) == 0 && determineBit(b2, 7) == 0 && bitTotal2 == 4 && (b1 == 139 || b1 == 99 || b1 == 137)) {
          i += 3;
          break;
        }
        else if((b1 == 139 && determineBit(b2, 6) == 0 && determineBit(b2, 7) == 0 && bitTotal2 == 5) || ((b1 == 139 || b1 == 99 || b1 == 137) && determineBit(b2, 6) == 0 && determineBit(b2, 7) == 0 && bitTotal2 == 5)){
          unsigned int offset = (unsigned int)7 + i + off + *((unsigned int *)(assembleAddr + i + 3));
          printValues(relas, syms, lines, offset, relaCount, 0);
          i += 6;
          break;
        }
      }
    default :{
      return;
    }

    }
  }
}

static int determineBit(unsigned int byte, int location) {
  if((byte & (1 << location))){
    return 1;
  }
  else{
    return 0;
  }
}

static int findFunctionLength(Elf64_Ehdr *ehdr, unsigned int off) {

  int i;
  Elf64_Shdr *symTab = sectionByName(ehdr, ".symtab");
  Elf64_Sym *sym = AT_SEC(ehdr, symTab);
  int size = symTab->sh_size / sizeof(Elf64_Sym);

  for (i = 0; i < size; i++) {
    if (sym[i].st_value * 2 >= off && sym[i].st_value <= off) {
      return (sym[i].st_value * 2) - off;
    }
  }
  return 0;
}

static void printValues(Elf64_Rela *relas, Elf64_Sym *syms, char *lines, unsigned int off, int relaCount, int flag) {

  int i;
  unsigned long relaOffset = -1;

  for (i = 0; i < relaCount; i++) {
    if (relas[i].r_offset == off) {
      relaOffset = ELF64_R_SYM(relas[i].r_info);
      if (flag == 0 && ELF64_ST_BIND(syms[relaOffset].st_info) == STB_GLOBAL && relaOffset != -1){
        printf("  %s\n",lines + syms[relaOffset].st_name);
      }
      else if (flag == 1 && relaOffset != -1) {
        printf("  (%s)\n",lines + syms[relaOffset].st_name);
      }
      break;
    }
  }
}

static void fail(char *reason, int err_code) {
  fprintf(stderr, "%s (%d)\n", reason, err_code);
  exit(1);
}
