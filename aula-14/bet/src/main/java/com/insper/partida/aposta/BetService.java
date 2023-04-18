package com.insper.partida.aposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetService {

    @Autowired
    private BetRespository betRespository;


    public Bet saveBet(Bet bet) {
        Game game = gameService.getGame(bet.getGame().getIdentifier());
        if (game == null) {
            throw new RuntimeException("Game not found");
        }

        bet.setGame(game);
        return betRespository.save(bet);


    }

    public List<Bet> listBets() {
        return betRespository.findAll();
    }

    public Bet verifyBet(Integer betId) {
        Bet bet = betRespository.findById(betId).get();

        if (bet == null) {
            throw new RuntimeException("Bet not found");
        }

        Game game = bet.getGame();

        if (game.getScoreHome() > game.getScoreAway()) {
            if (bet.getResult() == BetResult.HOME) {
                bet.setStatus(BetStatus.WON);
            } else {
                bet.setStatus(BetStatus.LOST);
            }
        } else if (game.getScoreHome() < game.getScoreAway()) {
            if (bet.getResult() == BetResult.AWAY) {
                bet.setStatus(BetStatus.WON);
            } else {
                bet.setStatus(BetStatus.LOST);
            }
        } else {
            if (bet.getResult() == BetResult.DRAW) {
                bet.setStatus(BetStatus.WON);
            } else {
                bet.setStatus(BetStatus.LOST);
            }
        }

        return bet;
    }

}
