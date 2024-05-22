package theAya.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.actions.AyaCameraAction;
import theAya.characters.TheAya;

import static theAya.AyaMod.makeCardPath;

public class TenguDailyReport extends AbstractDynamicCard {

    public static final String ID = AyaMod.makeID(TenguDailyReport.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("TenguDailyReport.png");

    public static final String NAME = cardStrings.NAME;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;
    public static final String[] TEXT = cardStrings.EXTENDED_DESCRIPTION;
    public static final int BASE_MAGIC_NUMBER = 30;


    private static final int COST = 2;

    // /STAT DECLARATION/

    public TenguDailyReport() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int cost_up = 1;
        if(TheAya.getWindSpeed()>=baseMagicNumber){
                TheAya.loseWindSpeed(baseMagicNumber);
                cost_up = 0;
        }
        addToBot(new AyaCameraAction(AbstractDungeon.player,1,TEXT,false,cost_up,2));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-5);
            initializeDescription();
        }
    }
}