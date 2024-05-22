package theAya.cards;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.ui.WindSpeedDisplayUnit.WindType;

import java.util.Random;

import static theAya.AyaMod.makeCardPath;

public class OffensiveStance extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(OffensiveStance.class.getSimpleName());
    public static final String IMG = makeCardPath("OffensiveStance.png");




    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public OffensiveStance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        TheAya.changeWindType(WindType.ATTACK);
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 3), 3));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 3), 3));
        if(upgraded){
            TheAya.gainWindSpeed(10);
        }
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