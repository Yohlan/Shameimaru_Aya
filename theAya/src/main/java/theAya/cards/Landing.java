package theAya.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.powers.FlightPower;

import static theAya.AyaMod.makeCardPath;


public class Landing extends AbstractDynamicCard {

    public static final String ID = AyaMod.makeID(Landing.class.getSimpleName());
    public static final String IMG = makeCardPath("Landing.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    public static final String NAME = cardStrings.NAME;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;


    private static final int COST = 0;


    public Landing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 1;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.hasPower(FlightPower.POWER_ID)){
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, magicNumber), magicNumber));
            addToBot(new ReducePowerAction(p, p, FlightPower.POWER_ID, p.getPower(FlightPower.POWER_ID).amount));
        }
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return AbstractDungeon.player.hasPower(FlightPower.POWER_ID);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}