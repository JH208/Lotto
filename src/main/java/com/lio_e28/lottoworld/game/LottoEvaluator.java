package com.lio_e28.lottoworld.game;

import java.util.HashSet;

public class LottoEvaluator {
    // 당첨 번호를 저장하는 필드
    private HashSet<Integer> winningNumbers;

    public LottoEvaluator() {
        winningNumbers = new HashSet<>();
    }

    // 당첨 번호를 설정하는 메서드
    public void setWinningNumbers(HashSet<Integer> winningNumbers) {
        this.winningNumbers = winningNumbers;
    }

    // 플레이어의 로또 번호와 당첨 번호를 비교하는 메서드
    public int evaluateTicket(HashSet<Integer> playerNumbers) {
        // 교집합을 구해 일치하는 번호의 개수를 계산
        HashSet<Integer> intersection = new HashSet<>(playerNumbers);
        intersection.retainAll(winningNumbers);
        return intersection.size();
    }

    // 당첨 번호를 반환하는 메서드
    public HashSet<Integer> getWinningNumbers() {
        return new HashSet<>(winningNumbers);
    }
    public String determineRank(int matchingNumbers) {
        switch (matchingNumbers) {
            case 6:
                return "1등";
            case 5:
                return "2등";
            case 4:
                return "3등";
            default:
                return "낙첨";
        }
    }

}
