/*    */ package theAya.potions;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.Color;
/*    */ import com.megacrit.cardcrawl.helpers.GameDictionary;
/*    */ import com.megacrit.cardcrawl.helpers.PowerTip;
/*    */ import com.megacrit.cardcrawl.helpers.TipHelper;
/*    */ import com.megacrit.cardcrawl.potions.AbstractPotion;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public abstract class BasePotion
/*    */   extends AbstractPotion
/*    */ {
/* 15 */   public static final Logger logger = LogManager.getLogger(BasePotion.class.getName());
/*    */   
/*    */   public BasePotion(String name, String id, PotionRarity rarity, PotionSize size, PotionColor color) {
/* 18 */     super(name, id, rarity, size, color);
/* 19 */     logger.info("new base potion:" + getClass().getName() + " id: " + id);
/*    */   }
/*    */ 
/*    */   
/*    */   public BasePotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect effect, Color liquidColor, Color hybridColor, Color spotsColor) {
/* 24 */     super(name, id, rarity, size, effect, liquidColor, hybridColor, spotsColor);
/* 25 */     logger.info("new base potion:" + getClass().getName() + " id: " + id);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void triggerOnTurnStart() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void triggerOnTurnEnd() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void triggerOnVictory() {}
/*    */ 
/*    */   
/*    */   public void triggerOnCombatStart() {}
/*    */ 
/*    */   
/*    */   public AbstractPotion makeCopy() {
/*    */     try {
/* 46 */       return (AbstractPotion)getClass().newInstance();
/* 47 */     } catch (InstantiationException|IllegalAccessException e) {
/* 48 */       throw new RuntimeException("failed to auto-generate makeCopy for potion: " + this.ID);
/*    */     } 
/*    */   }
/*    */   
/*    */   private String dedupeKeyword(String keyword) {
/* 53 */     String retVal = (String)GameDictionary.parentWord.get(keyword);
/* 54 */     if (retVal != null) {
/* 55 */       return retVal;
/*    */     }
/* 57 */     return keyword;
/*    */   }
/*    */   
/*    */   public void initializeDescription() {
/* 61 */     ArrayList<String> keywords = new ArrayList<>();
/*    */     
/* 63 */     for (String word : this.description.split(" ")) {
/* 64 */       String tmp = dedupeKeyword(word.trim().toLowerCase());
/* 65 */       if (GameDictionary.keywords.containsKey(tmp)) {
/* 66 */         keywords.add(tmp);
/*    */       }
/*    */     } 
/*    */     
/* 70 */     this.tips.clear();
/* 71 */     this.tips.add(new PowerTip(this.name, this.description));
/* 72 */     for (String keyword : keywords)
/* 73 */       this.tips.add(new PowerTip(TipHelper.capitalize(keyword), (String)GameDictionary.keywords.get(keyword))); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Fuyoh\Desktop\ShoujoKageki.jar!\ShoujoKageki\potions\BasePotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */