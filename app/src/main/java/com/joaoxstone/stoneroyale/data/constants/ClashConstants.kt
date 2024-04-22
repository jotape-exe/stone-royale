package com.joaoxstone.stoneroyale.data.constants

import com.joaoxstone.stoneroyale.R

class ClashConstants {
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
                54000020 to R.drawable.arena23,
                54000031 to R.drawable.arena24
            )

            return arenas[arenaId]
        }
    }

}