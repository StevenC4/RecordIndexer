package view.spell;

/**
 * Created by Steven on 1/9/14.
 */

public class TrieImpl implements Trie {
    Trie.Node root;

    public TrieImpl() {
        root = new NodeImpl();
    }

    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count
     *
     * @param word The word being added to the trie
     */
    public void add(String word) {
        add(root, word.toLowerCase());
    }

    private void add(Trie.Node currentNode, String word) {
        if (word.length() == 1) {
            if (((NodeImpl)currentNode).nodes[word.charAt(0) - 97] == null) {
                NodeImpl node = new NodeImpl();
                node.value++;
                ((NodeImpl)currentNode).nodes[word.charAt(0) - 97] = node;
            } else {
                ((NodeImpl)((NodeImpl)currentNode).nodes[word.charAt(0) - 97]).value++;
            }
        } else {
            if (((NodeImpl)currentNode).nodes[word.charAt(0) - 97] == null) {
                NodeImpl node = new NodeImpl();
                ((NodeImpl)currentNode).nodes[word.charAt(0) - 97] = node;
                add(node, word.substring(1));
            } else {
                add((((NodeImpl) currentNode).nodes[word.charAt(0) - 97]), word.substring(1));
            }
        }
    }

    /**
     * Searches the trie for the specified word
     *
     * @param word The word being searched for
     *
     * @return A reference to the trie node that represents the word,
     * 			or null if the word is not in the trie
     */
    public Trie.Node find(String word) {
        return find(root, word.toLowerCase());
    }

    private Trie.Node find(Trie.Node currentNode, String word) {
        if (word.length() == 1) {
            if (((NodeImpl)currentNode).nodes[word.charAt(0) - 97] != null && ((NodeImpl)currentNode).nodes[word.charAt(0) - 97].getValue() > 0) {
                return ((NodeImpl)currentNode).nodes[word.charAt(0) - 97];
            } else {
                return null;
            }
        } else {
            if (((NodeImpl)currentNode).nodes[word.charAt(0) - 97] != null) {
                return find(((NodeImpl)currentNode).nodes[word.charAt(0) - 97], word.substring(1));
            } else {
                return null;
            }
        }
    }

    /**
     * Returns the number of unique words in the trie
     *
     * @return The number of unique words in the trie
     */
    public int getWordCount() {
        return getWordCount(root);
    }

    private int getWordCount(Trie.Node currentNode) {
        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (((NodeImpl)currentNode).nodes[i] != null) {
                count += getWordCount(((NodeImpl)currentNode).nodes[i]);
            }
        }
        if (currentNode.getValue() > 0) {
            count++;
        }
        return count;
    }

    /**
     * Returns the number of nodes in the trie
     *
     * @return The number of nodes in the trie
     */
    public int getNodeCount() {
        return getNodeCount(root);
    }

    private int getNodeCount(Trie.Node currentNode) {
        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (((NodeImpl)currentNode).nodes[i] != null) {
                count += getNodeCount(((NodeImpl)currentNode).nodes[i]);
            }
        }
        return ++count;
    }

    /**
     * The toString specification is as follows:
     * For each word, in alphabetical order:
     * <word> <count>\n
     */
    @Override
    public String toString() {
        return toString(root, "");
    }

    private String toString(Trie.Node currentNode, String word) {
        StringBuilder wordsList = new StringBuilder();

        for (int i = 0; i < 26; i++) {
            if (((NodeImpl)currentNode).nodes[i] != null) {
                StringBuilder tempWord = new StringBuilder();
                tempWord.append(word);
                tempWord.append(Character.toChars(i + 97));

                if (((NodeImpl)currentNode).nodes[i].getValue() > 0) {
                    wordsList.append(tempWord.toString());
                    wordsList.append(" ");
                    wordsList.append(((NodeImpl)currentNode).nodes[i].getValue());
                    wordsList.append("\n");
                }

                String list = toString(((NodeImpl)currentNode).nodes[i], tempWord.toString());
                wordsList.append(list);
            }
        }
        return wordsList.toString();
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + getNodeCount();
        hash = hash * 31 + getWordCount();
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }

        TrieImpl other = (TrieImpl)o;
        return equals(root, other.root);
    }

    private boolean equals(Trie.Node currentThis, Trie.Node currentOther) {
        boolean equals = true;
        if (currentThis.getValue() != currentOther.getValue()) {
            return false;
        }
        for (int i = 0; i < 26; i++) {
            if (((NodeImpl)currentThis).nodes[i] != null && ((NodeImpl)currentOther).nodes[i] != null)
            {
                equals &= equals(((NodeImpl)currentThis).nodes[i], ((NodeImpl)currentOther).nodes[i]);
            } else if (((NodeImpl)currentThis).nodes[i] == null ^ ((NodeImpl)currentOther).nodes[i] == null) {
                return false;
            }
        }

        return equals;
    }
}
