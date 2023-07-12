#include <iostream>
#include <stdexcept>
#include <memory>
#include <cstring>

class Validate {
public:
    static void ok(bool condition, const std::string& message) {
        if (!condition) {
            throw std::invalid_argument(message);
        }
    }

    static void notNull(const void* value) {
        if (value == nullptr) {
            throw std::invalid_argument("Must not be null");
        }
    }

    static void stringLength(const std::string& value, int minLength, int maxLength) {
        ok(value.length() >= minLength, "Length must be at least " + std::to_string(minLength));
        ok(value.length() <= maxLength, "Length must be at most " + std::to_string(maxLength));
    }

    static void integerRange(int value, int minimum, int maximum) {
        ok(value >= minimum, "Must be at least " + std::to_string(minimum));
        ok(value <= maximum, "Must be at most " + std::to_string(maximum));
    }
};

enum Resizing {
    DOUBLE,
    HALF
};

class Stack {
private:
    std::unique_ptr<std::string[]> elements;
    int top;
    int capacity;

public:
    Stack(int capacity) {
        Validate::integerRange(capacity, 5, 100000);
        this->elements = std::unique_ptr<std::string[]>(new std::string[capacity]);
        this->top = -1;
        this->capacity = capacity;
    }

    void push(const std::string& item) {
        Validate::notNull(&item);
        Validate::stringLength(item, 1, 200);
        if (isFull()) {
            resize(Resizing::DOUBLE);
        }
        top++;
        elements[top] = item;
    }

    std::string pop() {
        if (isEmpty()) {
            throw std::runtime_error("Stack is empty");
        } else if (top < capacity / 4) {
            resize(Resizing::HALF);
        }
        std::string oldTopValue = elements[top];
        elements[top] = "";
        top--;
        return oldTopValue;
    }

    std::string peek() {
        return elements[top];
    }

    bool isEmpty() {
        return top == -1;
    }

    bool isFull() {
        return top == capacity - 1;
    }

private:
    void resize(Resizing resizing) {
        int newSize = (resizing == Resizing::DOUBLE) ? capacity * 2 : capacity / 2;
        std::unique_ptr<std::string[]> newElements(new std::string[newSize]);
        for (int i = 0; i <= top; i++) {
            newElements[i] = elements[i];
        }
        this->elements = std::move(newElements);
        this->capacity = newSize;
    }
};

int main() {
    Stack stack(5);
    stack.push("A");
    stack.push("B");
    stack.push("C");
    stack.push("D");
    stack.push("E");
    stack.push("F");
    stack.push("G");
    stack.push("H");
    stack.push("I");
    stack.push("J");
    stack.push("K");

    std::cout << stack.pop() << std::endl;
    std::cout << stack.pop() << std::endl;
    std::cout << stack.pop() << std::endl;
    std::cout << stack.pop() << std::endl;
    std::cout << stack.pop() << std::endl;
    std::cout << stack.pop() << std::endl;
    std::cout << stack.pop() << std::endl;

    return 0;
}
