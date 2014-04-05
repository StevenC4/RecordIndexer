package view.main.dialog;

import view.state.SuggestedWordState;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 4/4/14
 * Time: 9:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class SuggestedWordDialog extends JDialog {

    SuggestedWordState suggestedWordState;
    String suggestedWord;
    JList<String> suggestionsListBox;
    ListSelectionModel listModel;
    JButton useSuggestionButton;

    public SuggestedWordDialog(SuggestedWordState suggestedWordState) {
        this.suggestedWordState = suggestedWordState;
        suggestedWord = "";

        this.setTitle("Suggestions");
        this.setSize(new Dimension(215, 215));
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        String originalWord = suggestedWordState.getOriginalWord();
        boolean wholeWordCapitalized = wholeWordCapitalized(originalWord);
        boolean firstLetterCapitalized = firstLetterCapitalized(originalWord);
        Vector<String> v = new Vector<String>();
        for (String word : suggestedWordState.getSuggestedWords()) {
            if (wholeWordCapitalized) {
                v.add(word.toUpperCase());
            } else if (firstLetterCapitalized) {
                v.add(word.substring(0, 1).toUpperCase() + word.substring(1, word.length()));
            } else {
                v.add(word);
            }
        }

        suggestionsListBox = new JList<String>(v);
        listModel = suggestionsListBox.getSelectionModel();
        suggestionsListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModel.addListSelectionListener(new ListListener());

        JScrollPane suggestionsScrollPane = new JScrollPane(suggestionsListBox);
        suggestionsScrollPane.setPreferredSize(new Dimension(160, 140));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelButtonListener());
        useSuggestionButton = new JButton("Use Suggestion");
        useSuggestionButton.addActionListener(new UseSuggestionButtonListener());
        useSuggestionButton.setEnabled(false);

        c.insets = new Insets(3, 3, 3, 3);
        c.gridwidth = 2;
        this.add(suggestionsScrollPane, c);

        c.insets = new Insets(3, 3, 3, 3);
        c.gridwidth = 1;
        c.gridy = 1;
        this.add(cancelButton, c);

        c.insets = new Insets(3, 0, 3, 3);
        c.gridx = 1;
        this.add(useSuggestionButton, c);
    }

    private boolean wholeWordCapitalized(String word) {
        return word.equals(word.toUpperCase());
    }

    private boolean firstLetterCapitalized(String word) {
        String firstLetter = word.substring(0, 1);
        return firstLetter.equals(firstLetter.toUpperCase());
    }

    class ListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            boolean isAdjusting = e.getValueIsAdjusting();
            if (isAdjusting) {
                if (!useSuggestionButton.isEnabled()) {
                    useSuggestionButton.setEnabled(true);
                }

                String word = suggestionsListBox.getSelectedValue();
                suggestedWord = word;
            }
        }
    }

    class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SuggestedWordDialog.this.dispose();
        }
    }

    class UseSuggestionButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            suggestedWordState.setSelectedWord(suggestedWord);
            SuggestedWordDialog.this.dispose();
        }
    }
}
