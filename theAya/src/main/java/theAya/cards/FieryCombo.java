package theAya.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.powers.FieryComboPower;
import theAya.ui.WindSpeedDisplayUnit;

import static theAya.AyaMod.makeCardPath;

public class FieryCombo extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(FieryCombo.class.getSimpleName());
    public static final String IMG = makeCardPath("FieryCombo.png");



    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 2;

    // /STAT DECLARATION/


    public FieryCombo() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(TheAya.getWindSpeed() >= 20){
            TheAya.loseWindSpeed(20);
            TheAya.changeWindType(WindSpeedDisplayUnit.WindType.ATTACK);
        }
        addToBot(new ApplyPowerAction(p,p,new FieryComboPower(p,p)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }
}