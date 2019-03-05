/*     */ package me.Sporech;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Particle;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class PlayerListener implements Listener
/*     */ {
/*     */   public PlayerListener(MainClass plugin)
/*     */   {
/*  35 */     plugin.getServer().getPluginManager().registerEvents(this, plugin);
/*     */   }
/*     */   
/*  38 */   public static Map<Player, PortalGun> mapper = new HashMap();
/*     */   
/*     */   @EventHandler
/*     */   public void onLoginEvent(PlayerLoginEvent event) {
/*  42 */     mapper.put(event.getPlayer(), new PortalGun());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDamageEvent(EntityDamageEvent event) {
/*  47 */     org.bukkit.entity.Entity e = event.getEntity();
/*  48 */     if (((e instanceof Player)) && 
/*  49 */       (((HumanEntity)e).getInventory().getBoots().getType() == Material.IRON_BOOTS) && 
/*  50 */       (event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL)) {
/*  51 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @EventHandler
/*     */   public void onLogoutEvent(PlayerQuitEvent event)
/*     */   {
/*  59 */     Player player = event.getPlayer();
/*  60 */     ((PortalGun)mapper.get(player)).destroyPortals();
/*  61 */     mapper.remove(player);
/*  62 */     PortalGun.cooldown.remove(player);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void shoot(PlayerInteractEvent event)
/*     */   {
/*  68 */     Player player = event.getPlayer();
/*  69 */     if (player.getItemInHand().getType() == Material.WOOD_PICKAXE) {
/*  70 */       Location loc = player.getTargetBlock(null, 100).getLocation().clone().add(0.5D, 0.5D, 0.5D);
/*     */       
/*  72 */       ArrayList<Double> c = new ArrayList();
/*     */       
/*  74 */       if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
/*  75 */         c.add(Double.valueOf(0.1D));
/*  76 */         c.add(Double.valueOf(0.6D));
/*  77 */         c.add(Double.valueOf(0.9D));
/*     */ 
/*     */       }
/*  80 */       else if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
/*  81 */         c.add(Double.valueOf(0.9D));
/*  82 */         c.add(Double.valueOf(1.0D));
/*  83 */         c.add(Double.valueOf(0.0D));
/*     */       }
/*  85 */       List<Location> locs = line(player, 100);
/*  86 */       int angle = 0;
/*  87 */       for (Location loc2 : locs) {
/*  88 */         double x = new Random().nextDouble() + 0.15D;
/*  89 */         double y = new Random().nextDouble() + 0.15D;
/*  90 */         double z = new Random().nextDouble() + 0.15D;
/*  91 */         for (int i = 0; i < 5; i++) {
/*  92 */           player.getWorld().spawnParticle(Particle.REDSTONE, loc2.clone().add(0.0D, Math.sin(angle) * x, 0.0D), 0, ((Double)c.get(0)).doubleValue(), ((Double)c.get(1)).doubleValue(), ((Double)c.get(2)).doubleValue());
/*  93 */           player.getWorld().spawnParticle(Particle.REDSTONE, loc2.clone().add(Math.sin(angle) * y, 0.0D, 0.0D), 0, ((Double)c.get(0)).doubleValue(), ((Double)c.get(1)).doubleValue(), ((Double)c.get(2)).doubleValue());
/*  94 */           player.getWorld().spawnParticle(Particle.REDSTONE, loc2.clone().add(0.0D, 0.0D, Math.sin(angle) * z), 0, ((Double)c.get(0)).doubleValue(), ((Double)c.get(1)).doubleValue(), ((Double)c.get(2)).doubleValue());
/*     */         }
/*  96 */         angle++;
/*  97 */         if (angle >= 360) {
/*  98 */           angle = 0;
/*     */         }
/*     */       }
/* 101 */       player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 1.0F, 1.5F);
/* 102 */       if ((loc.getBlock().getType() == Material.QUARTZ_BLOCK) || (loc.getBlock().getType() == Material.IRON_BLOCK) || (loc.getBlock().getType() == Material.END_BRICKS)) {
/* 103 */         player.getWorld().playSound(loc, Sound.ENTITY_SHULKER_HURT_CLOSED, 10.0F, 0.5F);
/* 104 */         if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
/* 105 */           if (((PortalGun)mapper.get(player)).getBluePortal() == null) {
/* 106 */             ((PortalGun)mapper.get(player)).setBluePortal(new BluePortal(loc, (PortalGun)mapper.get(player), player, facing(player)));
/*     */           }
/*     */           else {
/* 109 */             ((PortalGun)mapper.get(player)).bp.setLocation(loc);
/* 110 */             ((PortalGun)mapper.get(player)).getBluePortal().setSurface(facing(player));
/*     */           }
/*     */         }
/* 113 */         if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
/* 114 */           if (((PortalGun)mapper.get(player)).getYellowPortal() == null) {
/* 115 */             ((PortalGun)mapper.get(player)).setYellowPortal(new YellowPortal(loc, (PortalGun)mapper.get(player), player, facing(player)));
/* 116 */             ((PortalGun)mapper.get(player)).getYellowPortal().setSurface(facing(player));
/*     */           }
/*     */           else {
/* 119 */             ((PortalGun)mapper.get(player)).yp.setLocation(loc);
/* 120 */             ((PortalGun)mapper.get(player)).getYellowPortal().setSurface(facing(player));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public List<Location> line(Player player, int distance)
/*     */   {
/* 129 */     List<Block> targets = player.getLineOfSight(null, distance);
/* 130 */     Location loc1 = player.getLocation().add(0.0D, 1.5D, 0.0D);
/* 131 */     Location loc2 = ((Block)targets.get(targets.size() - 1)).getLocation().add(0.5D, 0.5D, 0.5D);
/* 132 */     Location AtoB = new Location(player.getWorld(), loc2.getX() - loc1.getX(), loc2.getY() - loc1.getY(), loc2.getZ() - loc1.getZ());
/* 133 */     double length = Math.sqrt(AtoB.getX() * AtoB.getX() + AtoB.getY() * AtoB.getY() + AtoB.getZ() * AtoB.getZ());
/* 134 */     double length2 = length * 2.0D;
/* 135 */     Location increment = new Location(AtoB.getWorld(), AtoB.getX() / length2, AtoB.getY() / length2, AtoB.getZ() / length2);
/* 136 */     if (length > distance) {
/* 137 */       length = distance;
/*     */     }
/*     */     else {
/* 140 */       length = Math.floor(length);
/*     */     }
/* 142 */     length2 = length * 2.0D;
/* 143 */     List<Location> lotcs = new ArrayList();
/* 144 */     for (double x = 0.0D; x <= length2; x += 1.0D) {
/* 145 */       loc1.add(increment);
/* 146 */       lotcs.add(new Location(loc1.getWorld(), loc1.getX(), loc1.getY(), loc1.getZ()));
/*     */     }
/*     */     
/*     */ 
/* 150 */     return lotcs;
/*     */   }
/*     */   
/*     */   public BlockFace facing(Player player) {
/* 154 */     List<Block> targets = player.getLineOfSight(null, 50);
/* 155 */     if (targets.size() > 1) {
/* 156 */       Block tg = (Block)targets.get(targets.size() - 1);
/* 157 */       Block bf = (Block)targets.get(targets.size() - 2);
/* 158 */       if (tg.getY() - bf.getY() < 0) {
/* 159 */         return BlockFace.UP;
/*     */       }
/* 161 */       if (tg.getY() - bf.getY() > 0) {
/* 162 */         return BlockFace.DOWN;
/*     */       }
/* 164 */       if (tg.getZ() - bf.getZ() < 0) {
/* 165 */         return BlockFace.SOUTH;
/*     */       }
/* 167 */       if (tg.getZ() - bf.getZ() > 0) {
/* 168 */         return BlockFace.NORTH;
/*     */       }
/* 170 */       if (tg.getX() - bf.getX() < 0) {
/* 171 */         return BlockFace.EAST;
/*     */       }
/* 173 */       if (tg.getX() - bf.getX() > 0) {
/* 174 */         return BlockFace.WEST;
/*     */       }
/*     */       
/* 177 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 181 */     return null;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void BlockManip(PlayerInteractEvent event)
/*     */   {
/* 187 */     if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
/* 188 */       Player player = event.getPlayer();
/* 189 */       PlayerInventory inv = player.getInventory();
/*     */       
/*     */ 
/* 192 */       ItemStack cube = new ItemStack(57, 1);
/* 193 */       ItemMeta meta = cube.getItemMeta();
/* 194 */       meta.setDisplayName(ChatColor.WHITE + "cube");
/* 195 */       cube.setItemMeta(meta);
/* 196 */       if (player.getGameMode() != org.bukkit.GameMode.CREATIVE) {
/* 197 */         if (event.getClickedBlock().getType() == Material.DIAMOND_BLOCK)
/*     */         {
/*     */ 
/* 200 */           if (!player.getInventory().getItemInOffHand().equals(null))
/*     */           {
/*     */ 
/* 203 */             Block below = event.getClickedBlock().getLocation().add(0.0D, -2.0D, 0.0D).getBlock();
/* 204 */             if (below.getType().equals(Material.REDSTONE_BLOCK)) {
/* 205 */               below.setTypeId(0);
/*     */             }
/*     */             
/*     */ 
/* 209 */             event.getClickedBlock().setTypeId(0);
/* 210 */             inv.setItemInOffHand(cube);
/*     */           }
/*     */         }
/* 213 */         if (event.getClickedBlock().getType() == Material.WORKBENCH)
/*     */         {
/* 215 */           event.setCancelled(true);
/*     */           
/*     */ 
/* 218 */           if (inv.getItemInOffHand().getType().equals(Material.DIAMOND_BLOCK))
/*     */           {
/*     */ 
/* 221 */             inv.setItemInOffHand(null);
/*     */             
/*     */ 
/* 224 */             event.getClickedBlock().getLocation().add(0.0D, 1.0D, 0.0D).getBlock().setType(Material.DIAMOND_BLOCK);
/* 225 */             event.getClickedBlock().getLocation().add(0.0D, -1.0D, 0.0D).getBlock().setType(Material.REDSTONE_BLOCK);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\insan\Downloads\Portal.jar!\me\Sporech\PlayerListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */