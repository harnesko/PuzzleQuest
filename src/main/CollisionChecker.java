package main;

import entity.Entity;
import entity.EntityType;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) { // TODO: viktigt att förstå denna metod, speciellt switch casen.. undersök
        // här ska den fysiska kroppen (solid area rektangeln) checkas, INTE player tile:n eller nåt annat
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // TODO: fixa collision blocket så det passar nya skin, testa me nya tiles

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "walkup", "runup":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.collisionBoolean[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.collisionBoolean[entityRightCol][entityTopRow];

                if (tileNum1 == 1 || tileNum2 == 1) {
                    entity.collisionOn = true;
                    System.out.println("HEJ");
                }
                break;
            case "walkdown", "rundown":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.collisionBoolean[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.collisionBoolean[entityRightCol][entityBottomRow];

                if (tileNum1 == 1 || tileNum2 == 1) {
                    entity.collisionOn = true;
                    System.out.println("HEJ");
                }
                break;
            case "walkleft", "runleft":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.collisionBoolean[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.collisionBoolean[entityLeftCol][entityBottomRow];

                if (tileNum1 == 1 || tileNum2 == 1) {
                    entity.collisionOn = true;
                    System.out.println("HEJ");
                }
                break;
            case "walkright", "runright":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.collisionBoolean[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.collisionBoolean[entityRightCol][entityBottomRow];

                if (tileNum1 == 1 || tileNum2 == 1) {
                    entity.collisionOn = true;
                    System.out.println("HEJ");
                }
                break;
        }
    }

    public int checkObject(Entity entity, EntityType type) {
        int index = -1;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null){

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                if(type == EntityType.PLAYER){
                    // Get the object's solid area position
                    gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                    gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
                }else if(type == EntityType.NPC){
                    //check stuff 
                }
                switch (entity.direction) {
                    case "walkup", "runup" -> {
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // intersects är en solidArea metod, kollar om de överlappar
                            // som checkar om objekt kolliderar
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            //if (type == EntityType.PLAYER) {
                                index = i;
                            System.out.println("Index: " + index);
                            //}
                        }
                    }
                    case "walkdown", "rundown" -> {
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (type == EntityType.PLAYER) {
                                index = i;
                            }
                        }
                    }
                    case "walkleft", "runleft" -> {
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (type == EntityType.PLAYER) {
                                index = i;
                            }
                        }
                    }
                    case "walkright", "runright" -> {
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (type == EntityType.PLAYER) {
                                index = i;
                            }
                        }
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }

        }
        return index;
    }
    public int checkEntity(Entity entity, Entity[] target){
        int index = -1;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null){

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction) {
                    case "walkup", "runup" -> {
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) { // intersects är en solidArea metod
                            entity.collisionOn = true;
                            index = i;
                        }
                    }
                    case "walkdown", "rundown" -> {
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                    }
                    case "walkleft", "runleft" -> {
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                    }
                    case "walkright", "runright" -> {
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }
}
