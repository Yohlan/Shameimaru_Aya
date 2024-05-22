package theAya.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.powers.ConvectionPower;
import theAya.powers.ShiftingTenguPower;

import static theAya.AyaMod.makeCardPath;

public class Convection extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(Convection.class.getSimpleName());
    public static final String IMG = makeCardPath("Convection.png");



    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public Convection() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new ConvectionPower(p,p,1)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}