#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "stack.h"

#define STACK_UNDERFLOW -1
#define STRING_OUT_OF_RANGE -2
#define NULL_PUSH -3

struct S {
    unsigned int capacity;
    unsigned int top;
    char** items;
};

Stack create(unsigned int capacity) {
    Stack s = (Stack)malloc(sizeof(struct S));
    s->capacity = capacity;
    s->top = -1;
    s->items = (char**)malloc(sizeof(char*) * capacity);
    return s;
}

bool is_empty(const Stack s) {
    return s->top == -1;
}

bool is_full(Stack s) {
    return s->top == s->capacity - 1;
} 

void push(const Stack s, char* item) {

    if (strlen(item) > 200) {
        exit(STRING_OUT_OF_RANGE);
    }
    if (item == NULL) {
        exit(NULL_PUSH);
    } 

    if (is_full(s)) {
        s->items = realloc(s->items, sizeof(char*) * s->capacity * 2);
        s->capacity *= 2;
    }
    s->items[++s->top] = item;
}

char* pop(Stack s) {
    if (is_empty(s)) {
        exit(STACK_UNDERFLOW);
    } else if (s->top < s->capacity / 4) {
        s->items = realloc(s->items, sizeof(char*) * s->capacity / 2);
        s->capacity /= 2;
    }
    char* popped = s->items[s->top];
    s->items[s->top--] = NULL;
    return popped;
}

char* peek(Stack s) {
    if (is_empty(s)) {
        exit(STACK_UNDERFLOW);
    }
    return s->items[s->top];
}

void destroy(Stack s) {
    free(s->items);
    free(s);
}

int main() {
    Stack stack = create(2);
    push(stack, "Hello");
    push(stack, "World");
    push(stack, "How");
    push(stack, "Are");
    push(stack, "You");
    push(stack, "Today");
    push(stack, "?");
    printf("%s\n", pop(stack));
    printf("%s\n", pop(stack));
    printf("%s\n", pop(stack));
    printf("%s\n", pop(stack));
    destroy(stack);
    return 0;
}