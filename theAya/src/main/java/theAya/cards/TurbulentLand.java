package theAya.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.ui.WindSpeedDisplayUnit.WindType;

import java.util.Random;

import static theAya.AyaMod.makeCardPath;

public class TurbulentLand extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(TurbulentLand.class.getSimpleName());
    public static final String IMG = makeCardPath("TurbulentLand.png");




    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public TurbulentLand() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            Random random = new Random();
            WindType windType = WindType.values()[random.nextInt(WindType.values().length)];
            TheAya.changeWindType(windType);
        if(upgraded){
            TheAya.gainWindSpeed(20);
        }else{
            if(TheAya.getWindSpeed() >= 10){
                TheAya.loseWindSpeed(10);
                TheAya.gainWindSpeed(20);
            }
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}