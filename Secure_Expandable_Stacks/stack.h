#ifndef STACK_H
#define STACK_H

#include <stdbool.h>


typedef struct S* Stack;

Stack create(unsigned int capacity);
void push(const Stack s, char* item);
bool is_empty(const Stack s);
bool is_full(Stack s);
char* pop(Stack s);
char* peek(Stack s);
void destroy(Stack s); 

#endif