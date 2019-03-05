/*     */ package me.Sporech;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class Portal
/*     */ {
/*     */   public Player player;
/*     */   public Location location;
/*  17 */   public Portal portal = this;
/*     */   public PortalGun gun;
/*     */   public BlockFace surface;
/*     */   public int id;
/*  21 */   public Vector offset = new Vector(0, 0, 0);
/*  22 */   protected List<Location> elipsep = null;
/*     */   Colour colour;
/*     */   
/*     */   public Portal(Location loc, PortalGun pg, Player p, Colour c, BlockFace bf)
/*     */   {
/*  27 */     this.colour = c;
/*  28 */     setLocation(loc);
/*  29 */     setSurface(bf);
/*  30 */     this.gun = pg;
/*  31 */     this.player = p;
/*  32 */     this.elipsep = elipse3(this.player, this.location.getBlock(), this.surface, BlockFace.UP);
/*  33 */     if (this.surface == BlockFace.DOWN) {
/*  34 */       this.elipsep = elipse3(this.player, this.location.clone().add(0.0D, -1.0D, 0.0D).getBlock(), this.surface, BlockFace.UP);
/*     */     }
/*     */     
/*  37 */     this.id = MainClass.that.getServer().getScheduler().scheduleSyncRepeatingTask(MainClass.that, new Runnable()
/*     */     {
/*     */       public void run() {
/*  40 */         ArrayList<Double> c = new ArrayList();
/*  41 */         switch (Portal.this.colour) {
/*     */         case blue: 
/*  43 */           c.add(Double.valueOf(0.1D));
/*  44 */           c.add(Double.valueOf(0.6D));
/*  45 */           c.add(Double.valueOf(0.9D));
/*     */         case yellow: 
/*  47 */           c.add(Double.valueOf(0.9D));
/*  48 */           c.add(Double.valueOf(1.0D));
/*  49 */           c.add(Double.valueOf(0.0D));
/*     */         }
/*  51 */         for (Location l : Portal.this.elipsep) {
/*  52 */           Portal.this.player.getWorld().spawnParticle(org.bukkit.Particle.REDSTONE, l, 0, ((Double)c.get(0)).doubleValue(), ((Double)c.get(1)).doubleValue(), ((Double)c.get(2)).doubleValue());
/*     */         }
/*  54 */         for (final Entity e : Portal.this.location.getWorld().getNearbyEntities(Portal.this.location.clone().add(Portal.this.offset), 1.5D, 1.5D, 1.5D)) {
/*  55 */           if (!PortalGun.cooldown.containsKey(e)) {
/*  56 */             PortalGun.cooldown.put(e, Boolean.valueOf(false));
/*     */           }
/*  58 */           if (!((Boolean)PortalGun.cooldown.get(e)).booleanValue()) {
/*  59 */             PortalGun.cooldown.replace(e, Boolean.valueOf(true));
/*  60 */             MainClass.that.getServer().getScheduler().scheduleSyncDelayedTask(MainClass.that, new Runnable()
/*     */             {
/*     */               public void run() {
/*  63 */                 PortalGun.cooldown.replace(e, Boolean.valueOf(false));
/*     */               }
/*  65 */             }, 20L);
/*  66 */             Vector v = null;
/*  67 */             float pitch = e.getLocation().getPitch();
/*  68 */             float yaw = e.getLocation().getYaw();
/*  69 */             switch (Portal.this.colour) {
/*     */             case blue: 
/*  71 */               v = Portal.this.switchvelocity(e.getVelocity(), Portal.this.gun.yp, Portal.this.gun.bp);
/*  72 */               switch (Portal.this.gun.yp.surface) {
/*     */               case NORTH: 
/*  74 */                 yaw = -90.0F;
/*  75 */                 break;
/*     */               case NORTH_EAST: 
/*  77 */                 yaw = 90.0F;
/*  78 */                 break;
/*     */               case DOWN: 
/*  80 */                 pitch = -180.0F;
/*  81 */                 yaw = 0.0F;
/*  82 */                 break;
/*     */               case EAST_NORTH_EAST: 
/*  84 */                 yaw = 0.0F;
/*  85 */                 pitch = 0.0F;
/*  86 */                 break;
/*     */               case EAST: 
/*  88 */                 pitch = -90.0F;
/*  89 */                 yaw = 0.0F;
/*  90 */                 break;
/*     */               case EAST_SOUTH_EAST: 
/*  92 */                 pitch = 90.0F;
/*  93 */                 yaw = 0.0F;
/*  94 */                 break;
/*     */               }
/*     */               
/*     */               
/*  98 */               Location l = new Location(Portal.this.gun.yp.getLocation().getWorld(), Portal.this.gun.yp.getLocation().getX(), Portal.this.gun.yp.getLocation().getY(), Portal.this.gun.yp.getLocation().getZ(), pitch, yaw);
/*  99 */               e.teleport(l.clone().add(Portal.this.gun.yp.offset));
/* 100 */               break;
/*     */             case yellow: 
/* 102 */               v = Portal.this.switchvelocity(e.getVelocity(), Portal.this.gun.bp, Portal.this.gun.yp);
/* 103 */               switch (Portal.this.gun.bp.surface) {
/*     */               case NORTH: 
/* 105 */                 yaw = -90.0F;
/* 106 */                 break;
/*     */               case NORTH_EAST: 
/* 108 */                 yaw = 90.0F;
/* 109 */                 break;
/*     */               case DOWN: 
/* 111 */                 pitch = -180.0F;
/* 112 */                 yaw = 0.0F;
/* 113 */                 break;
/*     */               case EAST_NORTH_EAST: 
/* 115 */                 pitch = 0.0F;
/* 116 */                 yaw = 0.0F;
/* 117 */                 break;
/*     */               case EAST: 
/* 119 */                 pitch = -90.0F;
/* 120 */                 yaw = 0.0F;
/* 121 */                 break;
/*     */               case EAST_SOUTH_EAST: 
/* 123 */                 pitch = 90.0F;
/* 124 */                 yaw = 0.0F;
/* 125 */                 break;
/*     */               }
/*     */               
/*     */               
/* 129 */               Location l1 = new Location(Portal.this.gun.bp.getLocation().getWorld(), Portal.this.gun.bp.getLocation().getX(), Portal.this.gun.bp.getLocation().getY(), Portal.this.gun.bp.getLocation().getZ(), pitch, yaw);
/* 130 */               e.teleport(l1.clone().add(Portal.this.gun.bp.offset));
/* 131 */               break;
/*     */             }
/*     */             
/*     */             
/* 135 */             e.setVelocity(v);
/*     */           }
/*     */         }
/*     */       }
/* 139 */     }, 0L, 1L);
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 143 */     return this.location;
/*     */   }
/*     */   
/*     */   public void setLocation(Location loc) {
/* 147 */     this.location = loc;
/*     */   }
/*     */   
/*     */   public void setSurface(BlockFace bf) {
/* 151 */     this.surface = bf;
/* 152 */     switch (this.surface) {
/*     */     case NORTH: 
/* 154 */       this.offset = new Vector(0, 1, 0);
/* 155 */       break;
/*     */     case NORTH_EAST: 
/* 157 */       this.offset = new Vector(0, -1, 0);
/* 158 */       break;
/*     */     case DOWN: 
/* 160 */       this.offset = new Vector(0, 0, -1);
/* 161 */       break;
/*     */     case EAST: 
/* 163 */       this.offset = new Vector(1, 0, 0);
/* 164 */       break;
/*     */     case EAST_NORTH_EAST: 
/* 166 */       this.offset = new Vector(0, 0, 1);
/* 167 */       break;
/*     */     case EAST_SOUTH_EAST: 
/* 169 */       this.offset = new Vector(-1, 0, 0);
/* 170 */       break;
/*     */     }
/*     */     
/*     */     
/* 174 */     this.elipsep = elipse3(this.player, this.location.getBlock(), this.surface, BlockFace.UP);
/* 175 */     if (this.surface == BlockFace.DOWN)
/* 176 */       this.elipsep = elipse3(this.player, this.location.clone().add(0.0D, -1.0D, 0.0D).getBlock(), this.surface, BlockFace.UP);
/*     */   }
/*     */   
/*     */   public BlockFace getSurface() {
/* 180 */     return this.surface;
/*     */   }
/*     */   
/*     */   static enum Colour {
/* 184 */     blue,  yellow;
/*     */   }
/*     */   
/* 187 */   public Vector switchvelocity(Vector vel, Portal p, Portal ap) { double carriedforce = 0.0D;
/* 188 */     switch (ap.surface) {
/*     */     case NORTH: 
/* 190 */       carriedforce = -vel.getY();
/* 191 */       break;
/*     */     case NORTH_EAST: 
/* 193 */       carriedforce = vel.getY();
/* 194 */       break;
/*     */     case DOWN: 
/* 196 */       carriedforce = vel.getZ();
/* 197 */       break;
/*     */     case EAST_NORTH_EAST: 
/* 199 */       carriedforce = -vel.getZ();
/* 200 */       break;
/*     */     case EAST_SOUTH_EAST: 
/* 202 */       carriedforce = vel.getX();
/* 203 */       break;
/*     */     case EAST: 
/* 205 */       carriedforce = -vel.getX();
/* 206 */       break;
/*     */     }
/*     */     
/*     */     
/* 210 */     switch (p.surface) {
/*     */     case NORTH: 
/* 212 */       vel = new Vector(0.0D, carriedforce, 0.0D);
/* 213 */       break;
/*     */     case NORTH_EAST: 
/* 215 */       vel = new Vector(0.0D, -carriedforce, 0.0D);
/* 216 */       break;
/*     */     case DOWN: 
/* 218 */       vel = new Vector(0.0D, 0.0D, -carriedforce);
/* 219 */       break;
/*     */     case EAST_NORTH_EAST: 
/* 221 */       vel = new Vector(0.0D, 0.0D, carriedforce);
/* 222 */       break;
/*     */     case EAST: 
/* 224 */       vel = new Vector(carriedforce, 0.0D, 0.0D);
/* 225 */       break;
/*     */     case EAST_SOUTH_EAST: 
/* 227 */       vel = new Vector(-carriedforce, 0.0D, 0.0D);
/* 228 */       break;
/*     */     }
/*     */     
/*     */     
/*     */ 
/* 233 */     return vel;
/*     */   }
/*     */   
/*     */   public List<Location> elipse3(Player player, Block block, BlockFace face, BlockFace flatDirection) {
/* 237 */     List<Location> locs = new ArrayList();
/* 238 */     double bX = block.getLocation().getX();
/* 239 */     double bY = block.getLocation().getY();
/* 240 */     double bZ = block.getLocation().getZ();
/*     */     
/* 242 */     if ((face == BlockFace.UP) || (face == BlockFace.DOWN)) {
/* 243 */       bX += 0.5D;
/* 244 */       bY += 1.0D;
/* 245 */       bZ += 1.0D;
/*     */       
/* 247 */       for (double x = 0.0D; x <= 0.25D; x += 0.05D) {
/* 248 */         double z = Math.sqrt(1.0D - 4.0D * x * x);
/* 249 */         locs.add(new Location(block.getWorld(), x + bX, 0.0D + bY, z + bZ));
/* 250 */         locs.add(new Location(block.getWorld(), x + bX, 0.0D + bY, -z + bZ));
/* 251 */         locs.add(new Location(block.getWorld(), -x + bX, 0.0D + bY, z + bZ));
/* 252 */         locs.add(new Location(block.getWorld(), -x + bX, 0.0D + bY, -z + bZ));
/*     */       }
/* 254 */       for (double z = 0.0D; z <= 25.0D; z += 0.05D) {
/* 255 */         double x = Math.sqrt((1.0D - z * z) / 4.0D);
/* 256 */         locs.add(new Location(block.getWorld(), x + bX, 0.0D + bY, z + bZ));
/* 257 */         locs.add(new Location(block.getWorld(), x + bX, 0.0D + bY, -z + bZ));
/* 258 */         locs.add(new Location(block.getWorld(), -x + bX, 0.0D + bY, z + bZ));
/* 259 */         locs.add(new Location(block.getWorld(), -x + bX, 0.0D + bY, -z + bZ));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 264 */     if ((face == BlockFace.NORTH) || (face == BlockFace.SOUTH) || (face == BlockFace.EAST) || (face == BlockFace.WEST)) {
/* 265 */       bY += 1.0D;
/*     */       
/*     */ 
/* 268 */       if ((face == BlockFace.NORTH) || (face == BlockFace.SOUTH))
/*     */       {
/*     */ 
/* 271 */         if (face == BlockFace.SOUTH) {
/* 272 */           bX += 0.5D;
/* 273 */           bZ += 1.0D;
/*     */ 
/*     */         }
/* 276 */         else if (face == BlockFace.NORTH) {
/* 277 */           bX += 0.5D;
/*     */         }
/*     */         
/* 280 */         for (double x = 0.0D; x <= 0.5D; x += 0.05D) {
/* 281 */           double y = Math.sqrt(1.0D - 4.0D * x * x);
/* 282 */           locs.add(new Location(block.getWorld(), x + bX, y + bY, bZ));
/* 283 */           locs.add(new Location(block.getWorld(), x + bX, -y + bY, bZ));
/* 284 */           locs.add(new Location(block.getWorld(), -x + bX, y + bY, bZ));
/* 285 */           locs.add(new Location(block.getWorld(), -x + bX, -y + bY, bZ));
/*     */         }
/* 287 */         for (double y = 0.0D; y <= 0.5D; y += 0.05D) {
/* 288 */           double x = Math.sqrt((1.0D - y * y) / 4.0D);
/* 289 */           locs.add(new Location(block.getWorld(), x + bX, y + bY, bZ));
/* 290 */           locs.add(new Location(block.getWorld(), x + bX, -y + bY, bZ));
/* 291 */           locs.add(new Location(block.getWorld(), -x + bX, y + bY, bZ));
/* 292 */           locs.add(new Location(block.getWorld(), -x + bX, -y + bY, bZ));
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 297 */       else if ((face == BlockFace.EAST) || (face == BlockFace.WEST))
/*     */       {
/*     */ 
/* 300 */         if (face == BlockFace.WEST) {
/* 301 */           bZ += 0.5D;
/*     */ 
/*     */         }
/* 304 */         else if (face == BlockFace.EAST) {
/* 305 */           bZ += 0.5D;
/* 306 */           bX += 1.0D;
/*     */         }
/*     */         
/* 309 */         for (double z = 0.0D; z <= 0.5D; z += 0.05D) {
/* 310 */           double y = Math.sqrt(1.0D - 4.0D * z * z);
/* 311 */           locs.add(new Location(block.getWorld(), bX, y + bY, z + bZ));
/* 312 */           locs.add(new Location(block.getWorld(), bX, y + bY, -z + bZ));
/* 313 */           locs.add(new Location(block.getWorld(), bX, -y + bY, z + bZ));
/* 314 */           locs.add(new Location(block.getWorld(), bX, -y + bY, -z + bZ));
/*     */         }
/* 316 */         for (double y = 0.0D; y <= 0.5D; y += 0.05D) {
/* 317 */           double z = Math.sqrt((1.0D - y * y) / 4.0D);
/* 318 */           locs.add(new Location(block.getWorld(), bX, y + bY, z + bZ));
/* 319 */           locs.add(new Location(block.getWorld(), bX, y + bY, -z + bZ));
/* 320 */           locs.add(new Location(block.getWorld(), bX, -y + bY, z + bZ));
/* 321 */           locs.add(new Location(block.getWorld(), bX, -y + bY, -z + bZ));
/*     */         }
/*     */       }
/*     */     }
/* 325 */     return locs;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 329 */     return this.id;
/*     */   }
/*     */ }


/* Location:              C:\Users\insan\Downloads\Portal.jar!\me\Sporech\Portal.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */