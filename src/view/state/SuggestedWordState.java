package view.state;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 4/4/14
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class SuggestedWordState {

    Set<String> suggestedWords;
    String selectedWord;
    String originalWord;

    public SuggestedWordState(Set<String> suggestedWords, String originalWord) {
        this.suggestedWords = suggestedWords;
        this.selectedWord = "";
        this.originalWord = originalWord;
    }

    public String getSelectedWord() {
        return selectedWord;
    }

    public void setSelectedWord(String selectedWord) {
        this.selectedWord = selectedWord;
    }

    public Set<String> getSuggestedWords() {
        return suggestedWords;
    }

    public String getOriginalWord() {
        return originalWord;
    }

}
