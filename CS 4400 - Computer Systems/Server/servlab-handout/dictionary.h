
/* A dictionary maps a string to a pointer. The pointer case be
   anything, such as another string. */

/* Opaque type for a dictionary instance: */
typedef struct dictionary_t dictionary_t;

/* A function provided to make_dictionary() that is used to destroy
   each values in the dictionary when the dictionary itself is
   destroyed or when the value is replaced with another value: */
typedef void (*free_proc_t)(void*);

/* Select between case-sensitive and case-insensitive
   string comparsion for dictionar keys: */
#define COMPARE_CASE_SENS   0
#define COMPARE_CASE_INSENS 1

/* Creates a dictionary with the given comparion mode and
   value-destruction function: */
dictionary_t *make_dictionary(int compare_mode, free_proc_t free_value);

/* Destroys a dictionary, which frees all key strings --- and also
   destroys all values using the function provuded to
   make_dictionary(): */
void free_dictionary(dictionary_t *d);

/* Sets the dictionary's mapping for `key` to `value`, destroying the
   value (if any) that `key` is currently mapped to. The dictionary
   makes its own copy of `key` and does not refer to that pointer on
   return. It keeps the `value` pointer as-is and effectively takes
   ownership of the value. */
void dictionary_set(dictionary_t *d, const char *key, void *value);

/* Returns the dictionary's value for `key`, or NULL if the dictionary
   has no value for `key`. The dictionary retains ownership of the
   result value, so beware that the result can be destroyed if the
   mapping for `key` is changed. */
void *dictionary_get(dictionary_t *d, const char *key);

/* Returns the number of keys/values that are mapped in the
   dictionary: */
size_t dictionary_count(dictionary_t *d);

/* Returns one key in the dictionary, where `i` is between 0
   (inclusive) and dictionary_count(d) (exclusive). */
const char *dictionary_key(dictionary_t *d, size_t i);

/* Returns one value in the dictionary, where `i` is between 0
   (inclusive) and dictionary_count(d) (exclusive). */
void *dictionary_value(dictionary_t *d, size_t i);
