package theAya.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.characters.TheAya;

import static theAya.AyaMod.makeCardPath;

public class WindBlade extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(WindBlade.class.getSimpleName());
    public static final String IMG = makeCardPath("WindBlade.png");



    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int BASE_MAGIC_NUMBER = 10;

    // /STAT DECLARATION/


    public WindBlade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = BASE_MAGIC_NUMBER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = baseDamage;

        if(TheAya.getWindSpeed()>=baseMagicNumber){
            TheAya.loseWindSpeed(baseMagicNumber);
            amount += 8;
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, amount, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));

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