package theAya.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.powers.FlightPower;

import static theAya.AyaMod.makeCardPath;

public class WindSurge extends AbstractDynamicCard {

    public static final String ID = AyaMod.makeID(WindSurge.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("WindSurge.png");

    public static final String NAME = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int BASE_MAGIC_NUMBER = 20;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;


    private static final int COST = 0;

    // /STAT DECLARATION/

    public WindSurge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(TheAya.getWindSpeed()>= this.baseMagicNumber){
            TheAya.loseWindSpeed(baseMagicNumber);
            addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, 2), 2));
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
            upgradeMagicNumber(-5);
            initializeDescription();
        }
    }
}