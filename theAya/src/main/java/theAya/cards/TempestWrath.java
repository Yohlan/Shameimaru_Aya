package theAya.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.powers.TempestWrathPower;

import static theAya.AyaMod.makeCardPath;

public class TempestWrath extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(TempestWrath.class.getSimpleName());
    public static final String IMG = makeCardPath("TempestWrath.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;




    public TempestWrath() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(BaseModCardTags.FORM);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new TempestWrathPower(p, p, 1), 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
