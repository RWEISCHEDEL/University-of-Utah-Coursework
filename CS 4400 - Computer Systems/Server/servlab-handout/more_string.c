#include <stdlib.h>
#include <ctype.h>
#include <stdarg.h>
#include <string.h>
#include <stdio.h>
#include "dictionary.h"
#include "more_string.h"

char *append_strings(const char *s, ...)
{
  const char *s2;
  char *result;
  va_list ap;
  size_t len = 0, pos = 0;

  va_start(ap, s);
  s2 = s;
  while (s2 != NULL) {
    len += strlen(s2);
    s2 = va_arg(ap, const char*);
  }
  va_end(ap);

  result = malloc(len + 1);

  pos = 0;

  va_start(ap, s);
  s2 = s;
  while (s2 != NULL) {
    len = strlen(s2);
    memcpy(result + pos, s2, len);
    pos += len;
    s2 = va_arg(ap, const char*);
  }
  va_end(ap);

  result[pos] = 0;

  return result;
}

char *to_string(long v) {
  char buffer[64];
  snprintf(buffer, sizeof(buffer), "%ld", v);
  return strdup(buffer);
}

int starts_with(char *starts, char *s) {
  size_t len;
  len = strlen(starts);
  return !strncmp(starts, s, len);
}

int parse_three(const char *buf,
                char **one_p, char **two_p, char **three_p,
                int extra_space_ok) {
  char *s1, *s2;
  size_t len, len1, len2, len3;

  len = strlen(buf);

  /* Should have "\r\n" at end: */
  if ((len < 2)
      || (buf[len-2] != '\r')
      || (buf[len-1] != '\n'))
    return 0;
  len -=2;

  s1 = strchr(buf, ' ');
  if (!s1)
    return 0;

  s2 = strchr(s1+1, ' ');
  if (!s2)
    return 0;

  if (!extra_space_ok
      && strchr(s2+1, ' '))
    return 0;

  len1 = s1 - buf;
  len2 = s2 - (s1 + 1);
  len3 = len - len1 - len2 - 2;

  if (one_p)
    *one_p = strndup(buf, len1);
  if (two_p)
    *two_p = strndup(s1+1, len2);
  if (three_p)
    *three_p = strndup(s2+1, len3);

  return 1;
}

int parse_request_line(const char *buf,
                       char **method_p, char **uri_p, char **version_p) {
  return parse_three(buf, method_p, uri_p, version_p, 0);
}

int parse_status_line(const char *buf,
                      char **version_p, char **status_p, char **desc_p) {
  return parse_three(buf, version_p, status_p, desc_p, 1);
}

void parse_header_line(char *buf, dictionary_t *d) {
  char *s, *name;
  size_t len;

  s = strchr(buf, ':');
  if (s) {
    name = strndup(buf, s - buf);

    /* skip leading whitespace */
    s++;
    while (isspace(*(unsigned char*)s))
      s++;
    /* strip trailing whitespace */
    len = strlen(s);
    while (len && isspace(((unsigned char *)s)[len-1]))
      --len;
      
    dictionary_set(d, name, strndup(s, len));

    free(name);
  }
}

#define IS_QSEP(c) (((c) == '&') || ((c) == ';'))
#define IS_END(c)  (((c) == 0) || ((c) == '#'))

void parse_query(const char *buf, dictionary_t *d) {
  const char *name_start;
  char *name, *d_name;
  const char *data_start;
  char *data, *d_data;
  
  while (!IS_END(*buf)) {
    name_start = buf;

    while (!IS_END(*buf) && (*buf != '=') && !IS_QSEP(*buf))
      buf++;

    name = strndup(name_start, buf - name_start);

    if (!IS_END(*buf) && !IS_QSEP(*buf))
      buf++;
    data_start = buf;

    while (!IS_END(*buf) && !IS_QSEP(*buf))
      buf++;

    data = strndup(data_start, buf - data_start);

    d_name = query_decode(name);
    d_data = query_decode(data);

    dictionary_set(d, name, d_data);

    free(d_name);
    free(name);
    free(data);

    if (!IS_END(*buf))
      buf++;
  }
}

void parse_uriquery(const char *buf, dictionary_t *d) {
  char *s;

  s = strchr(buf, '?');
  if (s)
    parse_query(s+1, d);
}

static int ishexdigit(int v) {
  return (((v >= '0') && (v <= '9'))
          || ((v >= 'A') && (v <= 'F'))
          || ((v >= 'a') && (v <= 'f')));
}

static int hex_value(int v) {
  if ((v >= '0') && (v <= '9'))
    return v - '0';
  else if ((v >= 'A') && (v <= 'F'))
    return v - 'A' + 10;
  else
    return v - 'a' + 10;
}

char *query_decode(const char *data) {
  int i, j;
  char *dest = NULL;

  /* two passes: one to size, then one to fill */
  while (1) {
    for (i = j = 0; data[i]; i++, j++) {
      if ((data[i] == '%')
          && (ishexdigit(data[i+1]))
          && (ishexdigit(data[i+2]))) {
        if (dest)
          dest[j] = hex_value(data[i+1]) * 16 + hex_value(data[i+2]);
        i += 2;
      } else if (dest) {
        if (data[i] == '+')
          dest[j] = ' ';
        else
          dest[j] = data[i];
      }
    }

    if (dest) {
      dest[j] = 0;
      return dest;
    }

    dest = malloc(j + 1);
  }
}

static int hex_digit(int v) {
  if (v < 10)
    return '0' + v;
  else
    return 'a' + (v - 10);
}

char *query_encode(const char *data) {
  int i, j;
  char *dest = NULL;

  /* two passes: one to size, then one to fill */
  while (1) {
    for (i = j = 0; data[i]; i++, j++) {
      if (((data[i] >= 'a') && (data[i] <= 'z'))
          || ((data[i] >= 'A') && (data[i] <= 'Z'))
          || ((data[i] >= '0') && (data[i] <= '9'))) {
        if (dest)
          dest[j] = data[i];
      } else {
        if (dest) {
          dest[j] = '%';
          dest[j+1] = hex_digit(((unsigned char *)data)[i] >> 4);
          dest[j+2] = hex_digit(((unsigned char *)data)[i] & 0xF);
        }
        j += 2;
      }
    }

    if (dest) {
      dest[j] = 0;
      return dest;
    }

    dest = malloc(j + 1);
  }
}

char *entity_encode(const char *data) {
  int i, j;
  char *dest = NULL;

  /* two passes: one to size, then one to fill */
  while (1) {
    for (i = j = 0; data[i]; i++, j++) {
      if ((data[i] == '<')
          || (data[i] == '>')) {
        if (dest) {
          dest[j] = '&';
          dest[j+1] = ((data[i] == '<') ? 'l' : 'g');
          dest[j+2] = 't';
          dest[j+3] = ';';
        }
        j += 3;
        
      } else if (data[i] == '&') {
        if (dest) {
          dest[j] = '&';
          dest[j+1] = 'a';
          dest[j+2] = 'm';
          dest[j+3] = 'p';
          dest[j+4] = ';';
        }
        j += 4;
      } else if (data[i] == '"') {
        if (dest) {
          dest[j] = '&';
          dest[j+1] = 'q';
          dest[j+2] = 'u';
          dest[j+3] = 'o';
          dest[j+4] = 't';
          dest[j+5] = ';';
        }
        j += 5;
      } else {
        if (dest)
          dest[j] = data[i];
      }
    }

    if (dest) {
      dest[j] = 0;
      return dest;
    }

    dest = malloc(j + 1);
  }  
}
