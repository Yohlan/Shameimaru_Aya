package theAya.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theAya.AyaMod;
import theAya.characters.TheAya;

import static theAya.AyaMod.makeCardPath;

public class GaleAssault extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(GaleAssault.class.getSimpleName());
    public static final String IMG = makeCardPath("GaleAssault.png");



    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int BASE_MAGIC_NUMBER = 15;

    // /STAT DECLARATION/


    public GaleAssault() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = BASE_MAGIC_NUMBER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        int tem_strength = 0;
        int speed = TheAya.getWindSpeed();
        while(speed >= baseMagicNumber){
            TheAya.loseWindSpeed(baseMagicNumber);
            speed = TheAya.getWindSpeed();
            tem_strength++;
        }
        if(tem_strength>0) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, tem_strength), tem_strength));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, tem_strength), tem_strength));
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
}