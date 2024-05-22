package theAya.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.actions.AyaCameraAction;
import theAya.characters.TheAya;
import theAya.relics.AyaCamera;


import java.util.ArrayList;

import static theAya.AyaMod.makeCardPath;

public class Instamatic extends AbstractDynamicCard {

    public static final String ID = AyaMod.makeID(Instamatic.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Instamatic.png");

    public static final String NAME = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;
    public static final String[] TEXT = cardStrings.EXTENDED_DESCRIPTION;


    private static final int COST = 1;

    // /STAT DECLARATION/

    public Instamatic() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.isEthereal = true;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new AyaCameraAction(AbstractDungeon.player,1,TEXT,false));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isEthereal = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}