package theAya.cards;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.ui.WindSpeedDisplayUnit;

import static theAya.AyaMod.makeCardPath;

public class WindGodGirl extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(WindGodGirl.class.getSimpleName());
    public static final String IMG = makeCardPath("WindGodGirl.png");




    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public WindGodGirl() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = 2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(TheAya.getWindSpeed()>=10){
            TheAya.loseWindSpeed(10);
            TheAya.changeWindType(WindSpeedDisplayUnit.WindType.SOAR);
        }
        if(TheAya.windType == WindSpeedDisplayUnit.WindType.SOAR){
            int windSpeed = TheAya.getWindSpeed();
            int value = windSpeed * baseMagicNumber - windSpeed;
            TheAya.gainWindSpeed(value);
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}