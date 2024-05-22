package theAya.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.powers.TremblePower;

import static theAya.AyaMod.makeCardPath;

public class TenguAura extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(TenguAura.class.getSimpleName());
    public static final String IMG = makeCardPath("TenguAura.png");



    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 3;

    // /STAT DECLARATION/


    public TenguAura() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = 2;
        addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) m, (AbstractCreature) p, (AbstractPower) new TremblePower(m,p,amount ),amount ));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
            initializeDescription();
        }
    }
}