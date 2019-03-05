/*    */ package me.Sporech;
/*    */ 
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ public class PortalGun
/*    */ {
/*  9 */   public static java.util.Map<org.bukkit.entity.Entity, Boolean> cooldown = new java.util.HashMap();
/*    */   public BluePortal bp;
/*    */   public YellowPortal yp;
/*    */   
/*    */   public void setBluePortal(BluePortal b)
/*    */   {
/* 15 */     this.bp = b;
/*    */   }
/*    */   
/* 18 */   public void setYellowPortal(YellowPortal y) { this.yp = y; }
/*    */   
/*    */   public BluePortal getBluePortal()
/*    */   {
/* 22 */     return this.bp;
/*    */   }
/*    */   
/* 25 */   public YellowPortal getYellowPortal() { return this.yp; }
/*    */   
/*    */   public void destroyPortals()
/*    */   {
/* 29 */     MainClass.that.getServer().getScheduler().cancelTask(this.bp.id);
/* 30 */     MainClass.that.getServer().getScheduler().cancelTask(this.yp.id);
/* 31 */     this.bp = null;
/* 32 */     this.yp = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\insan\Downloads\Portal.jar!\me\Sporech\PortalGun.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */