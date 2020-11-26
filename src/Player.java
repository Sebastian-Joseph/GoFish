import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
public class Player {
    private final List<Card> hand;
    private final List<String> books;
    private Card previousCard;
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
    public Card getCardByNeed(ArrayList<Card> oppTook, ArrayList<Card> oppDoesNotHave) {
        List<Card> potentialPick = hand;
        if(potentialPick.size() <= 4){
            previousCard = potentialPick.get(0);
            return potentialPick.get(0);
        }
        else{
            HashMap<Card, Integer> frequencies = new HashMap<>();
            for (int i = 0; i < hand.size() - 1; i++) {
                int count = 1;
                if(oppTook.size() > 4 && oppDoesNotHave.size() > 4){
                    for(int k = oppTook.size()-1; k > oppTook.size()-4; k--){
                        if(hand.get(i).getRank().equals(oppTook.get(k).getRank())){
                            potentialPick.remove(hand.get(i));
                        }
                    }
                    for(int l = oppDoesNotHave.size() - 1; l > oppDoesNotHave.size() - 4; l--){
                        if(hand.get(i).getRank().equals(oppTook.get(l).getRank())){
                            potentialPick.remove(hand.get(i));
                        }
                    }
                }
                for (int j = i + 1; j < potentialPick.size(); j++) {
                    if (potentialPick.get(i).equals(potentialPick.get(j))) {
                        count++;
                    }
                    if (!frequencies.containsKey(potentialPick.get(i))){
                        frequencies.put(potentialPick.get(i), count);
                    }
                }
            }
            int max = 0;
            Card mostOften = null;
            for(Card i : frequencies.keySet()){
                try {
                    if(!i.getRank().equals(previousCard.getRank())){
                        int a = frequencies.get(i);
                        if(a > max){
                            mostOften = i;
                        }
                    }
                }catch(NullPointerException e){
                    int a = frequencies.get(i);
                    if(a > max){
                        mostOften = i;
                    }
                }
                if(previousCard != null) {
                    if(!i.getRank().equals(previousCard.getRank())){
                        int a = frequencies.get(i);
                        if(a > max){
                            mostOften = i;
                        }
                    }
                }
            }
            previousCard = mostOften;
            return mostOften;
        }
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