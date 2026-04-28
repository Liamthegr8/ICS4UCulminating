// for (int i=0; i<map.mapRooms.length; i++) {
//                 for (int j=0; j<map.mapRooms.length; j++) {
//                     Room r = map.mapRooms[i][j];
//                     if (r != null) {
//                         for (int k=0; k<r.roomTiles.length; k++) {
//                             for (int l=0; l<r.roomTiles.length; l++) {
//                                 Tile t = r.roomTiles[k][l];
//                                 if (t != null) {
//                                     Rectangle tile = new Rectangle(i*r.roomSize + k*Tile.tileSize, j*r.roomSize + l*Tile.tileSize, Tile.tileSize, Tile.tileSize);
//                                     //LR Checks
//                                     //stop interference with player 1px in gnd collision
//                                     // if (isPlayerGrounded) {
//                                     //     player.y -=1;
//                                     // }
//                                     if (player.intersects(tile)) {
//                                         //check right
//                                         if (player.x + player.width <= tile.x) { //Tile.tileSize/2
//                                             //isPlayerTouchingRightWall = true;
//                                             player.vx = 0;
//                                             player.x = tile.x - player.width+1;
                                            
//                                         }
//                                         //check left
//                                         if (tile.x + Tile.tileSize <= player.x) { //player.width/2
//                                             //isPlayerTouchingLeftWall = true;
//                                             player.vx = 0;
//                                             player.x = tile.x + Tile.tileSize-1;
                                            
//                                         }   
//                                     }
//                                     // if (isPlayerGrounded) {
//                                     //     //set player y position back to normal
//                                     //     player.y +=1;
//                                     // }

                                    
//                                     //UD Checks
//                                     //stop interference with LR walls touching
//                                     //has to use player reference for vars as not updated yet (bool=false)
//                                     // if (player.isTouchingRightWall) {
//                                     //     player.x -= 1;
//                                     // }
//                                     // if (player.isTouchingLeftWall) {
//                                     //     player.x += 1;
//                                     // } 
//                                     if (player.intersects(tile)) {
//                                         //for the lengths/2 I could have done + velocity instead, this seemed more intuitive FOR NOW, given CURRENT SIZES

//                                         //check if player is on top of tile
//                                         if (player.y + player.height <= tile.y) { //+ Tile.tileSize/2
//                                             //isPlayerGrounded = true;
//                                             //set player 1 px deep in blocks
//                                             player.y = tile.y - player.height + 0;
//                                         }
//                                         //check if player is hitting above
//                                         if (tile.y + Tile.tileSize <= player.y) {
//                                             player.vy = 0;
//                                             player.y = tile.y + Tile.tileSize;
                                            
//                                         }
//                                         // if (!(player.y + player.height <= tile.y + Tile.tileSize/2) && !(tile.y + Tile.tileSize <= player.y + player.height/2)) {
//                                         //     //Inside Tile
//                                         //     player.vy = 0;
//                                         // }
//                                     }

//                                     if (!player.intersects(tile)) {
//                                         player.y += 1;
//                                         if (player.intersects(tile)) {
//                                             isPlayerGrounded = true;
//                                         }
//                                         player.y -= 1;
//                                     }
//                                     //set to normal
//                                     // if (player.isTouchingRightWall) {
//                                     //     player.x += 1;
//                                     // }
//                                     // if (player.isTouchingLeftWall) {
//                                     //     player.x -= 1;
//                                     // }
                                    

                                    


//                                 }
//                             }
//                         }
//                     }
//                 }
//             }
//             player.isGrounded = isPlayerGrounded;
//             // player.isTouchingRightWall = isPlayerTouchingRightWall;
//             // player.isTouchingLeftWall = isPlayerTouchingLeftWall;