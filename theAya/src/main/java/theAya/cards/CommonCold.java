package theAya.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.powers.TremblePower;

import static theAya.AyaMod.makeCardPath;

public class CommonCold extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(CommonCold.class.getSimpleName());
    public static final String IMG = makeCardPath("CommonCold.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;
    private int addAmount = 0;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public CommonCold() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 6;
        this.baseMagicNumber = 10;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addAmount = 0;
        if(TheAya.getWindSpeed() >= baseMagicNumber){
            TheAya.loseWindSpeed(baseMagicNumber);
            addAmount = 2;
        }
        addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, 3+addAmount), 3+addAmount));
        if(TheAya.getWindSpeed() >= baseMagicNumber){
            TheAya.loseWindSpeed(baseMagicNumber);
            addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) m, (AbstractCreature) p, (AbstractPower) new TremblePower(m,p,1 ),1 ));
        }
    }

    //Upgraded stats.
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(-5);
            initializeDescription();
        }
    }

    private final Color dGlowColor = this.glowColor;
    @Override
    public void triggerOnGlowCheck() {
        if (TheAya.getWindSpeed() < baseMagicNumber) {
            this.glowColor = dGlowColor;
        } else {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
}