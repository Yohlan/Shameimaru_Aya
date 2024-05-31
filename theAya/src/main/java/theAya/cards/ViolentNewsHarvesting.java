package theAya.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.actions.ViolentNewsAction;
import theAya.characters.TheAya;

import static theAya.AyaMod.makeCardPath;

public class ViolentNewsHarvesting extends AbstractDynamicCard {

    public static final String ID = AyaMod.makeID(ViolentNewsHarvesting.class.getSimpleName());
    public static final String IMG = makeCardPath("ViolentNewsHarvesting.png");
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;


    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final int BASE_MAGIC_NUMBER = 15;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;

    // /STAT DECLARATION/

    public ViolentNewsHarvesting() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        this.baseDamage = 6;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if(TheAya.getWindSpeed() >= magicNumber){
            addToBot(new ViolentNewsAction(p, 0, m));
            TheAya.loseWindSpeed(magicNumber);
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(-5);
            initializeDescription();
        }
    }

}