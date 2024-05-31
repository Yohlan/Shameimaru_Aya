package theAya.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theAya.AyaMod;
import theAya.characters.TheAya;

import static theAya.AyaMod.makeCardPath;

public class FullSpeedImpact extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(FullSpeedImpact.class.getSimpleName());
    public static final String IMG = makeCardPath("FullSpeedImpact.png");



    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 5;
    private static final int BASE_MAGIC_NUMBER = 20;

    // /STAT DECLARATION/


    public FullSpeedImpact() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = BASE_MAGIC_NUMBER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        float multiplier = 1;
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, (int)(damage*multiplier), damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        int speed = TheAya.getWindSpeed();
        int counter = 0;
        while(speed >= baseMagicNumber){
            TheAya.loseWindSpeed(baseMagicNumber);
            multiplier *= 1.05F;
            speed = TheAya.getWindSpeed();
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, (int)(damage*multiplier), damageTypeForTurn),
                            AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            counter++;
            if(counter >=10) break;
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(-5);
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