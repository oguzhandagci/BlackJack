import java.util.Scanner;
public class Game {

    public static void main(String[] args) {
        System.out.println("Welcome to Blackjack Game!");

        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffle();

        Deck playerDeck = new Deck();
        Deck dealerDeck = new Deck();

        double playerMoney = 100.00;

        Scanner scanner = new Scanner(System.in);

        while (playerMoney > 0) {
            System.out.println("You have $" + playerMoney + " How much are you going to bet?");
            double playerBet = scanner.nextDouble();
            if (playerBet > playerMoney) {
                System.out.println("You cannot bet more than you have!");
                break;
            }

            boolean endRound = false;

            playerDeck.draw(playingDeck);
            playerDeck.draw(playingDeck);

            dealerDeck.draw(playingDeck);
            dealerDeck.draw(playingDeck);
//
            while (true) {
                System.out.println("Your hand:");
                System.out.println(playerDeck.toString());
                System.out.println("Your hand is valued at: " + playerDeck.cardsValue());
                System.out.println("Dealer hand is valued at: " + dealerDeck.getCard(0).toString() + " and [Hidden]");
                System.out.println("Would you like to (1)Hit or (2)Stand?");
                int response = scanner.nextInt();
                if (response == 1) {
                    playerDeck.draw(playingDeck);
                    System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize() - 1).toString());
                    if (playerDeck.cardsValue() > 21) {
                        System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
                        playerMoney -= playerBet;
                        endRound = true;
                        break;
                    }
                }
                if (response == 2) {
                    break;
                }
            }
            System.out.println("Dealer Cards: " +dealerDeck.toString());
            while ((dealerDeck.cardsValue() < 17) && endRound == false){
                dealerDeck.draw(playingDeck);
                System.out.println("Dealer Draws: " + dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
            }
            System.out.println("Dealer's hand is valued at: " +dealerDeck.cardsValue());
            if ((dealerDeck.cardsValue() > 21)&& endRound == false){
                System.out.println("You Won!");
                playerMoney += playerBet;
                endRound = true;
            }
            else if ((playerDeck.cardsValue() == dealerDeck.cardsValue()) && endRound == false){
                System.out.println("Push");
                endRound = true;
            }
            else if((playerDeck.cardsValue() > dealerDeck.cardsValue()) && endRound == false){
                System.out.println("You Won!");
                playerMoney += playerBet;
                endRound = true;
            } else if ((dealerDeck.cardsValue() > playerDeck.cardsValue()) && endRound == false) {
                System.out.println("Dealer Won!");
                playerMoney-=playerBet;
                endRound = true;
            }

            playerDeck.moveAllToDeck(playingDeck);
            dealerDeck.moveAllToDeck(playingDeck);
            System.out.println("End of hand.");
        }
        System.out.println("Game over. You lost.");
    }
}