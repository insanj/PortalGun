/*    */ package me.Sporech;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.Server;
/*    */ 
/*    */ public class MainClass extends org.bukkit.plugin.java.JavaPlugin
/*    */ {
/*    */   public static org.bukkit.plugin.Plugin that;
/*    */   
/*    */   public void onEnable()
/*    */   {
/* 12 */     that = this;
/* 13 */     new PlayerListener(this);
/* 14 */     new PortalGun();
/* 15 */     org.bukkit.Bukkit.getLogger().info("It's been a long time...");
/* 16 */     for (org.bukkit.entity.Player player : getServer().getOnlinePlayers()) {
/* 17 */       PlayerListener.mapper.put(player, new PortalGun());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\insan\Downloads\Portal.jar!\me\Sporech\MainClass.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */