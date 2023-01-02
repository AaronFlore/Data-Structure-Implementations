/** 
 ** A word finder program that represents a collection of non-empty, non-blank words, optimized for autocompletion
 ** Implemented with a Ternary Search Tree
 **/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TreeNode {
    char data;
    boolean end;
    TreeNode left, middle, right;

    public TreeNode(char data) {
        this.data = data;
        this.end = false;
        this.left = null;
        this.middle = null;
        this.right = null;
    }
}

class WordFinder {
    private TreeNode root;
    private ArrayList<String> list;

    public WordFinder() {
        root = null;
    }

    public void add(String word) {
        word = word.trim();
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        root = add(root, word.toCharArray(), 0);
    }

    public TreeNode add(TreeNode node, char[] word, int charIndex) {
        if (node == null)
            node = new TreeNode(word[charIndex]);
        if (word[charIndex] < node.data)
            node.left = add(node.left, word, charIndex);
        else if (word[charIndex] > node.data)
            node.right = add(node.right, word, charIndex);
        else {
            if (charIndex + 1 < word.length)
                node.middle = add(node.middle, word, charIndex + 1);
            else
                node.end = true;
        }
        return node;
    }

    public boolean contains(String word) {
        word = word.strip();
        return contains(root, word.toCharArray(), 0);
    }

    private boolean contains(TreeNode node, char[] word, int charIndex) {
        if (node == null)
            return false;
        if (word[charIndex] < node.data)
            return contains(node.left, word, charIndex);
        else if (word[charIndex] > node.data)
            return contains(node.right, word, charIndex);
        else {
            if (node.end && charIndex == word.length - 1)
                return true;
            else if (charIndex == word.length - 1)
                return false;
            else
                return contains(node.middle, word, charIndex + 1);
        }
    }

    public List<String> allWords() {
        list = new ArrayList<String>();
        findAllWords(root, "");
        Collections.sort(list);
        return list;
    }

    private void findAllWords(TreeNode node, String word) {
        if (node != null) {
            findAllWords(node.left, word);
            word = word + node.data;
            if (node.end)
                list.add(word);
            findAllWords(node.middle, word);
            word = word.substring(0, word.length() - 1);
            findAllWords(node.right, word);
        }
    }

    public List<String> suggestions(String prefix) {
        prefix = prefix.trim();
        if (prefix == null || prefix.isEmpty()) {
            throw new IllegalArgumentException();
        }

        list = new ArrayList<String>();
        findSuggestions(root, "", prefix);
        Collections.sort(list);
        return list;
    }

    private void findSuggestions(TreeNode node, String word, String prefix) {
        if (node != null) {
            findSuggestions(node.left, word, prefix);
            word = word + node.data;
            if (node.end && word.startsWith(prefix))
                list.add(word);
            findSuggestions(node.middle, word, prefix);
            word = word.substring(0, word.length() - 1);
            findSuggestions(node.right, word, prefix);
        }
    }
}