package theAya.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.localization.CardStrings;
import theAya.AyaMod;
import theAya.characters.TheAya;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static theAya.AyaMod.makeCardPath;

public class SpreadRumors extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(SpreadRumors.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("SpreadRumors.png");



    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int BASE_MAGIC_NUMBER = 1;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;



    public SpreadRumors() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC_NUMBER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p, (AbstractPower)new WeakPower((AbstractCreature)m, this.magicNumber, false), this.magicNumber));
        if(upgraded)
            addToBot((AbstractGameAction)
                new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p,
                        (AbstractPower)new VulnerablePower((AbstractCreature)m, this.magicNumber, false), this.magicNumber));
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