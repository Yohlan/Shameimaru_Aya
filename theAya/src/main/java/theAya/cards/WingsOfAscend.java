package theAya.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.powers.FlightPower;


import static theAya.AyaMod.makeCardPath;

public class WingsOfAscend extends AbstractDynamicCard {

    public static final String ID = AyaMod.makeID(WingsOfAscend.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("WingsOfAscend.png");

    public static final String NAME = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;


    private static final int COST = 0;

    // /STAT DECLARATION/

    public WingsOfAscend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 20;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(TheAya.getWindSpeed() >= baseMagicNumber){
            TheAya.loseWindSpeed(baseMagicNumber);
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new FlightPower((AbstractCreature)p,p,2), 2));
            this.exhaust = true;
            return;
        }
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new FlightPower((AbstractCreature)p, p,1), 1));
        this.exhaust = true;
    }
    @Override
    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot((AbstractGameAction)new MakeTempCardInHandAction((AbstractCard)this));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-5);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}