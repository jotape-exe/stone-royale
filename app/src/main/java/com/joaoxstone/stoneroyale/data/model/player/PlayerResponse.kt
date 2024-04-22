package com.joaoxstone.stoneroyale.data.model.player

import com.google.gson.annotations.SerializedName


data class PlayerResponse(
    @SerializedName("tag") var tag: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("expLevel") var expLevel: Int? = null,
    @SerializedName("trophies") var trophies: Int? = null,
    @SerializedName("bestTrophies") var bestTrophies: Int? = null,
    @SerializedName("wins") var wins: Int? = null,
    @SerializedName("losses") var losses: Int? = null,
    @SerializedName("battleCount") var battleCount: Int? = null,
    @SerializedName("threeCrownWins") var threeCrownWins: Int? = null,
    @SerializedName("challengeCardsWon") var challengeCardsWon: Int? = null,
    @SerializedName("challengeMaxWins") var challengeMaxWins: Int? = null,
    @SerializedName("tournamentCardsWon") var tournamentCardsWon: Int? = null,
    @SerializedName("tournamentBattleCount") var tournamentBattleCount: Int? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("donations") var donations: Int? = null,
    @SerializedName("donationsReceived") var donationsReceived: Int? = null,
    @SerializedName("totalDonations") var totalDonations: Int? = null,
    @SerializedName("warDayWins") var warDayWins: Int? = null,
    @SerializedName("clanCardsCollected") var clanCardsCollected: Int? = null,
    @SerializedName("clan") var clan: Clan? = Clan(),
    @SerializedName("arena") var arena: Arena? = Arena(),
    @SerializedName("leagueStatistics") var leagueStatistics: LeagueStatistics? = LeagueStatistics(),
    @SerializedName("badges") var badges: ArrayList<Badges> = arrayListOf(),
    @SerializedName("achievements") var achievements: ArrayList<Achievements> = arrayListOf(),
    @SerializedName("cards") var cards: ArrayList<Cards> = arrayListOf(),
    @SerializedName("supportCards") var supportCards: ArrayList<SupportCards> = arrayListOf(),
    @SerializedName("currentDeck") var currentDeck: ArrayList<CurrentDeck> = arrayListOf(),
    @SerializedName("currentDeckSupportCards") var currentDeckSupportCards: ArrayList<CurrentDeckSupportCards> = arrayListOf(),
    @SerializedName("currentFavouriteCard") var currentFavouriteCard: CurrentFavouriteCard? = CurrentFavouriteCard(),
    @SerializedName("starPoints") var starPoints: Int? = null,
    @SerializedName("expPoints") var expPoints: Int? = null,
    @SerializedName("legacyTrophyRoadHighScore") var legacyTrophyRoadHighScore: Int? = null,
    @SerializedName("currentPathOfLegendSeasonResult") var currentPathOfLegendSeasonResult: CurrentPathOfLegendSeasonResult? = CurrentPathOfLegendSeasonResult(),
    @SerializedName("lastPathOfLegendSeasonResult") var lastPathOfLegendSeasonResult: LastPathOfLegendSeasonResult? = LastPathOfLegendSeasonResult(),
    @SerializedName("bestPathOfLegendSeasonResult") var bestPathOfLegendSeasonResult: BestPathOfLegendSeasonResult? = BestPathOfLegendSeasonResult(),
    @SerializedName("totalExpPoints") var totalExpPoints: Int? = null

)