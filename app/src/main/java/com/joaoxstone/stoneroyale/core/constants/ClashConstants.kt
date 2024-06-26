package com.joaoxstone.stoneroyale.core.constants

import androidx.compose.ui.graphics.Color
import com.joaoxstone.stoneroyale.R

class ClashConstants private constructor() {
    companion object {
        fun getIconArena(arenaId: Int): Int? {
            val arenas = mutableMapOf(
                54000000 to R.drawable.arena0,
                54000001 to R.drawable.arena1,
                54000002 to R.drawable.arena2,
                54000003 to R.drawable.arena3,
                54000004 to R.drawable.arena4,
                54000005 to R.drawable.arena5,
                54000006 to R.drawable.arena6,
                54000008 to R.drawable.arena7,
                54000009 to R.drawable.arena8,
                540000010 to R.drawable.arena9,
                54000007 to R.drawable.arena10,
                54000024 to R.drawable.arena11,
                54000011 to R.drawable.arena12,
                54000055 to R.drawable.arena13,
                54000056 to R.drawable.arena14,
                54000012 to R.drawable.arena15,
                54000013 to R.drawable.arena16,
                54000014 to R.drawable.arena17,
                54000015 to R.drawable.arena18,
                54000016 to R.drawable.arena19,
                54000017 to R.drawable.arena20,
                54000018 to R.drawable.arena21,
                54000019 to R.drawable.arena22,
                54000020 to R.drawable.legendary,
            )

            return arenas[arenaId]
        }

        fun getIconLeague(leageNumber: Int): Int? {

            val leages = mutableMapOf(
                1 to R.drawable.league1,
                2 to R.drawable.league2,
                3 to R.drawable.league3,
                4 to R.drawable.league4,
                5 to R.drawable.league5,
                6 to R.drawable.league6,
                7 to R.drawable.league7,
                8 to R.drawable.league8,
                9 to R.drawable.league9,
                10 to R.drawable.league10
            )

            return leages[leageNumber]
        }

        fun getBackgroundByLeague(leageNumber: Int?): List<Color> {

            if (leageNumber === null) {
                return listOf(
                    Color(0xFF0027AD),
                    Color(0xFF860B40),
                    Color(0xFFCC0101)
                )
            }
            val colorsBrush = mapOf(
                1 to listOf(
                    Color(0xFF411B02),
                    Color(0xFF6F2F05),
                    Color(0xFF965107)
                ),
                2 to listOf(
                    Color(0xFF5A5A5A),
                    Color(0xFF7E7D7D),
                    Color(0xFF9E9E9E)
                ),
                3 to listOf(
                    Color(0xFFD69107),
                    Color(0xFFD58D10),
                    Color(0xFFF5DB33)
                ),
                4 to listOf(
                    Color(0xFFA00000),
                    Color(0xFFA31717),
                    Color(0xFF9E2F2F)
                ),
                5 to listOf(
                    Color(0xFF474757),
                    Color(0xFF45455F),
                    Color(0xFF5D5D85)
                ),
                6 to listOf(
                    Color(0xFF121286),
                    Color(0xFF2424B3),
                    Color(0xFF3636F0)
                ),
                7 to listOf(
                    Color(0xFF2B71A0),
                    Color(0xFF6BAEDB),
                    Color(0xFFBBBE61)
                ),
                8 to listOf(
                    Color(0xFF6F072B),
                    Color(0xFF5A1E33),
                    Color(0xFF792323)
                ),
                9 to listOf(
                    Color(0xFF003E99),
                    Color(0xFF1767DB),
                    Color(0xFF91BEFF)
                ),
                10 to listOf(
                    Color(0xFF5F06DB),
                    Color(0xFF9B06DB),
                    Color(0xFFB808A1)
                )
            )

            return colorsBrush[leageNumber]!!
        }

        fun getIconClan(clanId: Int?): Int {
            val clans = mapOf(
                null to R.drawable.no_clan,
                16000000 to R.drawable.flame_01,
                16000001 to R.drawable.flame_02,
                16000002 to R.drawable.flame_03,
                16000003 to R.drawable.flame_04,
                16000004 to R.drawable.sword_01,
                16000005 to R.drawable.sword_02,
                16000006 to R.drawable.sword_03,
                16000007 to R.drawable.sword_04,
                16000008 to R.drawable.bolt_01,
                16000009 to R.drawable.bolt_02,
                16000010 to R.drawable.bolt_03,
                16000011 to R.drawable.bolt_04,
                16000012 to R.drawable.crown_01,
                16000013 to R.drawable.crown_02,
                16000014 to R.drawable.crown_03,
                16000015 to R.drawable.crown_04,
                16000016 to R.drawable.arrow_01,
                16000017 to R.drawable.arrow_02,
                16000018 to R.drawable.arrow_03,
                16000019 to R.drawable.arrow_04,
                16000020 to R.drawable.diamond_star_01,
                16000021 to R.drawable.diamond_star_02,
                16000022 to R.drawable.diamond_star_03,
                16000023 to R.drawable.diamond_star_04,
                16000024 to R.drawable.skull_01,
                16000025 to R.drawable.skull_02,
                16000026 to R.drawable.skull_03,
                16000027 to R.drawable.skull_04,
                16000028 to R.drawable.skull_05,
                16000029 to R.drawable.skull_06,
                16000030 to R.drawable.moon_01,
                16000031 to R.drawable.moon_02,
                16000032 to R.drawable.moon_03,
                16000033 to R.drawable.pine_01,
                16000034 to R.drawable.pine_02,
                16000035 to R.drawable.pine_03,
                16000036 to R.drawable.traditional_star_01,
                16000037 to R.drawable.traditional_star_02,
                16000038 to R.drawable.traditional_star_03,
                16000039 to R.drawable.traditional_star_04,
                16000040 to R.drawable.traditional_star_05,
                16000041 to R.drawable.traditional_star_06,
                16000042 to R.drawable.star_shine_01,
                16000043 to R.drawable.star_shine_02,
                16000044 to R.drawable.star_shine_03,
                16000045 to R.drawable.diamond_01,
                16000046 to R.drawable.diamond_02,
                16000047 to R.drawable.diamond_03,
                16000048 to R.drawable.flag_a_01,
                16000049 to R.drawable.flag_a_02,
                16000050 to R.drawable.flag_a_03,
                16000051 to R.drawable.flag_b_01,
                16000052 to R.drawable.flag_b_02,
                16000053 to R.drawable.flag_b_03,
                16000054 to R.drawable.flag_c_03,
                16000055 to R.drawable.flag_c_04,
                16000056 to R.drawable.flag_c_05,
                16000057 to R.drawable.flag_c_06,
                16000058 to R.drawable.flag_c_07,
                16000059 to R.drawable.flag_c_08,
                16000060 to R.drawable.flag_d_01,
                16000061 to R.drawable.flag_d_02,
                16000062 to R.drawable.flag_d_03,
                16000063 to R.drawable.flag_d_04,
                16000064 to R.drawable.flag_d_05,
                16000065 to R.drawable.flag_d_06,
                16000066 to R.drawable.flag_f_01,
                16000067 to R.drawable.flag_f_02,
                16000068 to R.drawable.flag_g_01,
                16000069 to R.drawable.flag_g_02,
                16000070 to R.drawable.flag_i_01,
                16000071 to R.drawable.flag_i_02,
                16000072 to R.drawable.flag_h_01,
                16000073 to R.drawable.flag_h_02,
                16000074 to R.drawable.flag_h_03,
                16000075 to R.drawable.flag_j_01,
                16000076 to R.drawable.flag_j_02,
                16000077 to R.drawable.flag_j_03,
                16000078 to R.drawable.flag_k_01,
                16000079 to R.drawable.flag_k_02,
                16000080 to R.drawable.flag_k_03,
                16000081 to R.drawable.flag_k_04,
                16000082 to R.drawable.flag_k_05,
                16000083 to R.drawable.flag_k_06,
                16000084 to R.drawable.flag_l_01,
                16000085 to R.drawable.flag_l_02,
                16000086 to R.drawable.flag_l_03,
                16000087 to R.drawable.flag_m_01,
                16000088 to R.drawable.flag_m_02,
                16000089 to R.drawable.flag_m_03,
                16000090 to R.drawable.flag_n_01,
                16000091 to R.drawable.flag_n_02,
                16000092 to R.drawable.flag_n_03,
                16000093 to R.drawable.flag_n_04,
                16000094 to R.drawable.flag_n_05,
                16000095 to R.drawable.flag_n_06,
                16000096 to R.drawable.twin_peaks_01,
                16000097 to R.drawable.twin_peaks_02,
                16000098 to R.drawable.gem_01,
                16000099 to R.drawable.gem_02,
                16000100 to R.drawable.gem_03,
                16000101 to R.drawable.gem_04,
                16000102 to R.drawable.coin_01,
                16000103 to R.drawable.coin_02,
                16000104 to R.drawable.coin_03,
                16000105 to R.drawable.coin_04,
                16000106 to R.drawable.elixir_01,
                16000107 to R.drawable.elixir_02,
                16000108 to R.drawable.heart_01,
                16000109 to R.drawable.heart_02,
                16000110 to R.drawable.heart_04,
                16000111 to R.drawable.heart_03,
                16000112 to R.drawable.tower_01,
                16000113 to R.drawable.tower_02,
                16000114 to R.drawable.tower_03,
                16000115 to R.drawable.tower_04,
                16000116 to R.drawable.fan_01,
                16000117 to R.drawable.fan_02,
                16000118 to R.drawable.fan_03,
                16000119 to R.drawable.fan_04,
                16000120 to R.drawable.fugi_01,
                16000121 to R.drawable.fugi_02,
                16000122 to R.drawable.fugi_03,
                16000123 to R.drawable.fugi_04,
                16000124 to R.drawable.yingyang_01,
                16000125 to R.drawable.yingyang_02,
                16000126 to R.drawable.flag_c_01,
                16000127 to R.drawable.flag_c_02,
                16000128 to R.drawable.cherry_blossom_01,
                16000129 to R.drawable.cherry_blossom_02,
                16000130 to R.drawable.cherry_blossom_03,
                16000131 to R.drawable.cherry_blossom_04,
                16000132 to R.drawable.cherry_blossom_06,
                16000133 to R.drawable.cherry_blossom_05,
                16000134 to R.drawable.cherry_blossom_07,
                16000135 to R.drawable.cherry_blossom_08,
                16000136 to R.drawable.bamboo_01,
                16000137 to R.drawable.bamboo_02,
                16000138 to R.drawable.bamboo_03,
                16000139 to R.drawable.bamboo_04,
                16000140 to R.drawable.orange_01,
                16000141 to R.drawable.orange_02,
                16000142 to R.drawable.lotus_01,
                16000143 to R.drawable.lotus_02,
                16000144 to R.drawable.a_char_king_01,
                16000145 to R.drawable.a_char_king_02,
                16000146 to R.drawable.a_char_king_03,
                16000147 to R.drawable.a_char_king_04,
                16000148 to R.drawable.a_char_barbarian_01,
                16000149 to R.drawable.a_char_barbarian_02,
                16000150 to R.drawable.a_char_prince_01,
                16000151 to R.drawable.a_char_prince_02,
                16000152 to R.drawable.a_char_knight_01,
                16000153 to R.drawable.a_char_knight_02,
                16000154 to R.drawable.a_char_goblin_01,
                16000155 to R.drawable.a_char_goblin_02,
                16000156 to R.drawable.a_char_darkprince_01,
                16000157 to R.drawable.a_char_darkprince_02,
                16000158 to R.drawable.a_char_darkprince_03,
                16000159 to R.drawable.a_char_darkprince_04,
                16000160 to R.drawable.a_char_minipekka_01,
                16000161 to R.drawable.a_char_minipekka_02,
                16000162 to R.drawable.a_char_pekka_01,
                16000163 to R.drawable.a_char_pekka_02,
                16000164 to R.drawable.a_char_hammer_01,
                16000165 to R.drawable.a_char_hammer_02,
                16000166 to R.drawable.a_char_rocket_01,
                16000167 to R.drawable.a_char_rocket_02,
                16000168 to R.drawable.freeze_01,
                16000169 to R.drawable.freeze_02,
                16000170 to R.drawable.clover_01,
                16000171 to R.drawable.clover_02,
                16000172 to R.drawable.flag_h_04,
                16000173 to R.drawable.flag_e_02,
                16000174 to R.drawable.flag_i_03,
                16000175 to R.drawable.flag_e_01,
                16000176 to R.drawable.a_char_barbarian_03,
                16000177 to R.drawable.a_char_prince_03,
                16000178 to R.drawable.a_char_bomb_01,
                16000179 to R.drawable.a_char_bomb_02
            )

            return clans[clanId] ?: R.drawable.no_clan
        }

        fun getChestByChestName(chestName: String): Int {
            val lowerChestName = chestName.lowercase().replace(" ", "")

            val chest = mapOf(
                "woodenchest" to R.drawable.chest_wooden,
                "silverchest" to R.drawable.chest_silver,
                "goldenchest" to R.drawable.chest_golden,
                "magicalchest" to R.drawable.chest_magical,
                "giantchest" to R.drawable.chest_giant,
                "epicchest" to R.drawable.chest_epic,
                "legendarychest" to R.drawable.chest_legendary,
                "towertroopchest" to R.drawable.chest_towertroop,
                "megalightningchest" to R.drawable.chest_megalightning,
                "legendarychest" to R.drawable.chest_legendary,
                "royalwildchest" to R.drawable.chest_royalwild,
                "goldcrate" to R.drawable.chest_goldcrate,
                "plentifulgoldcrate" to R.drawable.chest_plentifulgoldcrate,
                "overflowinggoldcrate" to R.drawable.chest_overflowinggoldcrate,
            )

            return chest[lowerChestName] ?: R.drawable.ic_baseline_broken_image
        }

        const val CREATOR_BADGE = "https://api-assets.clashroyale.com/playerbadges/512/Gx7gSrp4LwTmOnxUQdo8z3kBHpp8sZmHtb1sHMQrqYo.png"
    }

}