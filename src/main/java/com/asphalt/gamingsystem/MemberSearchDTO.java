package com.asphalt.gamingsystem;

import java.util.List;

import com.asphalt.gamingsystem.Members;
import com.asphalt.gamingsystem.RechargeDto;
import com.asphalt.gamingsystem.Games;
import com.asphalt.gamingsystem.PlayedHistoryDto;

public class MemberSearchDTO {

    private Members member;
    private List<RechargeDto> rechargeHistory;
    private List<Games> games;
    private List<PlayedHistoryDto> playedHistory;

    // Constructors
    public MemberSearchDTO() { }

    public MemberSearchDTO(Members member, List<RechargeDto> rechargeHistory,
                           List<Games> games, List<PlayedHistoryDto> playedHistory) {
        this.member = member;
        this.rechargeHistory = rechargeHistory;
        this.games = games;
        this.playedHistory = playedHistory;
    }

    // Getters and Setters
    public Members getMember() {
        return member;
    }

    public void setMember(Members member) {
        this.member = member;
    }

    public List<RechargeDto> getRechargeHistory() {
        return rechargeHistory;
    }

    public void setRechargeHistory(List<RechargeDto> rechargeHistory) {
        this.rechargeHistory = rechargeHistory;
    }

    public List<Games> getGames() {
        return games;
    }

    public void setGames(List<Games> games) {
        this.games = games;
    }

    public List<PlayedHistoryDto> getPlayedHistory() {
        return playedHistory;
    }

    public void setPlayedHistory(List<PlayedHistoryDto> playedHistory) {
        this.playedHistory = playedHistory;
    }
}
