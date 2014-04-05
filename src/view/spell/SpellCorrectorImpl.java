package view.spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Steven on 1/14/14.
 */
public class SpellCorrectorImpl implements SpellCorrector {

    public Trie trie;

    @Override
    public void useDictionary(File dictionaryFile) throws IOException {
        trie = new TrieImpl();
        Scanner scanner = new Scanner(dictionaryFile);
        scanner.useDelimiter(",");
        while (scanner.hasNext()) {
            trie.add(scanner.next());
        }
    }

    @Override
    public Set<String> suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
        Set<String> suggestions = new TreeSet<String>();

        if (inputWord == null) {
            throw new NoSimilarWordFoundException();
        } else if (inputWord.equals("")) {
            suggestions.add("");
        } else if (trie.find(inputWord.toLowerCase()) != null) {
            suggestions.add(inputWord.toLowerCase());
        } else {
            Set<String> editDistanceOne = getEditDistanceOne(inputWord);
            Set<String> editDistanceTwo = getEditDistanceTwo(editDistanceOne);

            for (String string : editDistanceTwo) {
                if (trie.find(string) != null && trie.find(string).getValue() > 0) {
                    suggestions.add(string);
                }
            }

            if (suggestions.size() == 0) {
                throw new NoSimilarWordFoundException();
            }
        }
        return suggestions;
    }

    private Set<String> getEditDistanceOne(String word) {
        Set<String> words = new HashSet<String>();
        words.addAll(deletion(word.toLowerCase()));
        words.addAll(transposition(word.toLowerCase()));
        words.addAll(alteration(word.toLowerCase()));
        words.addAll(insertion(word.toLowerCase()));
        return words;
    }

    private Set<String> getEditDistanceTwo(Set<String> wordsIn) {
        Set<String> words = new HashSet<String>();
        words.addAll(wordsIn);
        for (String word : wordsIn) {
            words.addAll(deletion(word));
            words.addAll(transposition(word));
            words.addAll(alteration(word));
            words.addAll(insertion(word));
        }
        return words;
    }

    private Set<String> deletion(String word) {
        Set<String> words = new HashSet<String>();
        if (word.length() > 1) {
            for (int i = 0;i < word.length(); i++) {
                words.add(word.substring(0, i) + word.substring(i + 1, word.length()));
            }
        }
        return words;
    }

    private Set<String> transposition(String word) {
        Set<String> words = new HashSet<String>();
        if (word.length() > 1) {
            for (int i = 0; i < word.length() - 1; i++) {
                String newWord = word.substring(0, i) + word.charAt(i + 1) + word.charAt(i) + word.substring(i + 2, word.length());
                if (!newWord.equals(word)) {
                    words.add(newWord);
                }
            }
        }
        return words;
    }

    private Set<String> alteration(String word) {
        Set<String> words = new HashSet<String>();
        for (int i = 0; i < word.length(); i++) {
            Character charToSkip = word.charAt(i);
            for (int j = 0; j < 26; j++) {
                if (j != charToSkip - 97) {
                    words.add(word.substring(0, i) + Character.toChars(j + 97)[0] + word.substring(i + 1, word.length()));
                }
            }
        }
        return words;
    }

    private Set<String> insertion(String word) {
        Set<String> words = new HashSet<String>();
        for (int i = 0; i < word.length() + 1; i++) {
            for (int j = 0; j < 26; j++) {
                words.add(word.substring(0, i) + Character.toChars(j + 97)[0] + word.substring(i, word.length()));
            }
        }
        return words;
    }

    private boolean canAssignWord(Trie.Node node, Trie.Node suggestedNode, String word, String suggestedWord) {
        if (suggestedWord == null) {
            return true;
        } else if (node.getValue() > suggestedNode.getValue()) {
            return true;
        } else if (node.getValue() == suggestedNode.getValue() && word.compareTo(suggestedWord) < 0) {
            return true;
        } else {
            return false;
        }
    }
}
