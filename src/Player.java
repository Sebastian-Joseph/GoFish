import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class Player {
    private final List<Card> hand;
    private final List<String> books;
    public Player() {
        this.hand = new ArrayList<>();
        this.books = new ArrayList<>();
    }
    public List<Card> getHand() {
        return hand;
    }
    public List<String> getBooks() {
        return books;
    }
    public void takeCard(Card card) {
        hand.add(card);
        sortHand();
    }
    public boolean hasCard(Card card) {
        for (Card c : hand) {
            if (c.getRank().equals(card.getRank())) {
                return true;
            }
        }
        return false;
    }
    public void relinquishCard(Player player, Card card) {
        int index = findCard(card);
        if (index != -1) {
            Card c = hand.remove(index);
            player.getHand().add(c);
            sortHand();
            player.sortHand();
        }
    }
    public boolean findAndRemoveBooks() {
        for (int i = 0; i < hand.size() - 1; i++) {
            int frequency = 1;
            for (int j = i + 1; j < hand.size(); j++) {
                if (hand.get(i).getRank().equals(hand.get(j).getRank())) {
                    frequency++;
                }
            }
            if (frequency == 4) {
                return removeSets(i);
            }
        }
        return false;
    }
    public Card getCardByNeed() {
        int index = 0;
        int frequency = 1;
        for (int i = 0; i < hand.size() - 1; i++) {
            int count = 1;
            for (int j = i + 1; j < hand.size(); j++) {
                if (hand.get(i).getRank().equals(hand.get(j).getRank())) {
                    count++;
                }
            }
        }
        return hand.get(index);
    }
    private int findCard(Card card) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getRank().equals(card.getRank())) {
                return i;
            }
        }
        return -1;
    }
    private boolean removeSets(int index) {
        books.add(hand.get(index).getRank());
        for (int i = 0; i < 4; i++) {
            hand.remove(index);
        }
        sortHand();
        sortBooks();
        return true;
    }
    private void sortHand() {
        hand.sort((a,b) -> {
            if (Card.getOrderedRank(a.getRank()) == Card.getOrderedRank(b.getRank())) {
                return Card.getOrderedSuit(a.getSuit()) - Card.getOrderedRank(b.getRank());
            }
            return Card.getOrderedRank(a.getRank()) - Card.getOrderedRank(b.getRank());
        });
    }
    private void sortBooks() {
        books.sort(Comparator.comparingInt(Card::getOrderedRank));
    }
}