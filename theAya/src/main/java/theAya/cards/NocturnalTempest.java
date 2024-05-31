package theAya.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import theAya.AyaMod;
import theAya.actions.ModifyCardWindSpeedAction;
import theAya.characters.TheAya;
import theAya.powers.TremblePower;

import static theAya.AyaMod.makeCardPath;

public class NocturnalTempest  extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(NocturnalTempest .class.getSimpleName());
    public static final String IMG = makeCardPath("NocturnalTempest.png");



    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int BASE_MAGIC_NUMBER = 15;

    // /STAT DECLARATION/


    public NocturnalTempest () {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = BASE_MAGIC_NUMBER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        int amount = 1;
        if(TheAya.getWindSpeed()>=baseMagicNumber){
            TheAya.loseWindSpeed(baseMagicNumber);
            amount++;
        }
        addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) m, (AbstractCreature) p, (AbstractPower) new TremblePower(m,p,amount ),amount ));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-5);
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
    private final Color dGlowColor = this.glowColor;
    @Override
    public void triggerOnGlowCheck() {
        if (TheAya.getWindSpeed() < magicNumber) {
            this.glowColor = dGlowColor;
        } else {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
}