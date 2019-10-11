package com.example.myapplication.Entity;

public enum NEO_NUMS {
    NEOPI_QUESTION_SIZE(240), // NEOPI質問総数
    NEOFFI_QUESTION_SIZE(60), // NEOFFI質問総数
    FACTOR_SIZE(5), // 次元数
    LOWER_FACTOR_SIZE(6), // 下位次元数
    NEOPI_ITEM_SIZE(8), // NEOPIの各下位次元の質問数
    NEOFFI_ITEM_SIZE(2), // NEOFFIの項目数
    POSITION_REVERSE_SIGN(0),
    POSITION_QUESTION_SENTENCE(1);

    private final int num;

    private NEO_NUMS(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
