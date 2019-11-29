// //timeline to give sun to sunflower every 5s
// //timeline to shoot peas every 0.5s
// //timeline to explode cherrybomb after 1s
// //timeline to have plants refresh time
//rows me plants ka map to clear that cell

//add click event to plants to remove using shovel

// final double HOUSE_LAST_LINE = 250;
// final double RIGHTMOST_LINE  = 1100;

// void main_func()
// {
// 	Timeline mainTimer = new Timeline (new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>()
// 	{
// 		@Override
// 		public void handle(ActionEvent event)
// 		{
// 			for (Row row : allRows)
// 			{
// 				handlePlants(row);
// 				handlePeas(row);
// 				handleLawnMowers(row);
// 				handleZombies(row);
// 			}
// 		}
// 	}));

// 	mainTimer.setCycleCount(Timeline.INDEFINITE);
// 	mainTimer.play();
// }

// void handlePlants(Row row)
// {
// 	Set<Plants> plants = row.getPlants();

// 	for (Iterator<Plants> i = plants.iterator(); i.hasNext(); )
// 	{
// 		Plants plant = i.next();

// 		if (plant.getHealth() <= 0)
// 		{
// 			plant.getVBox().getChildren().clear();
// 			i.remove();
// 		}
// 	}
// }

// void handlePeas(Row row)
// {
// 	Set<Peas> peas = row.getPeas();
// 	boolean stillThere = false;

// 	for (Iterator<Peas> i = peas.iterator(); i.hasNext(); )
// 	{
// 		Peas pea = i.next();

// 		for (Zombies zombie : row.getZombies())
// 		{
// 			if (pea.getVBox().getBoundsInParent().intersects(zombie.getVBox().getBoundsInParent()))
// 			{
// 				zombie.decreaseHealth(pea.getAttack());
// 				if (pea.isFreezing()) zombie.freeze();
			
// 				pea.getVBox().getChildren().clear();
// 				i.remove();
				
// 				stillThere = false;
// 				break;
// 			}
// 		}

// 		if (stillThere) pea.getVBox().setLayoutX(pea.getVBox().getLayoutX() + pea.getDeltaX());
// 	}
// }

// void handleLawnMowers(Row row)
// {
// 	LawnMower lawnmower = row.getLawnMower();
// 	boolean moveLM = false;

// 	for (Zombies zombie : row.getZombies())
// 	{
// 		if (lawnmower==null && zombie.getLayoutX()<=HOUSE_LAST_LINE)
// 		{
// 			gameover();
// 			return;
// 		}

// 		if (lawnmower!=null && lawnmower.getVBox().getBoundsInParent().intersects(zombie.getVBox().getBoundsInParent()))
// 		{	
// 			zombie.decreaseHealth(10000);
// 			moveLM = true;
// 		}
// 	}

// 	if (moveLM) lawnmower.getVBox().setLayoutX(lawnmower.getVBox().getLayoutX() + lawnmower.getDeltaX());

// 	if (lawnmower!=null && lawnmower.getVBox().getLayoutX() >= RIGHTMOST_LINE)
// 	{
// 		lawnmower.getVBox().getChildren().clear();
// 		row.removeLawnMower();
// 	}
// }

// void handleZombies(Row row)
// {
// 	Set<Zombies> zombies = row.getZombies();

// 	for (Iterator<Zombies> i = zombies.iterator(); i.hasNext(); )
// 	{
// 		Zombies zombie = i.next();

// 		if (zombie.getHealth() <= 0)
// 		{
// 			zombie.getVBox().getChildren().clear();
// 			i.remove();
// 			continue;
// 		}

// 		for (Plants plant : row.getPlants())
// 		{
// 			if (zombie.getVBox().getBoundsInParent().intersects(plant.getVBox().getBoundsInParent()))
// 			{
// 				plant.decreaseHealth(zombie.getAttack());
// 				break;
// 			}
// 		}

// 		zombie.getVBox().setLayoutX(zombie.getVBox().getLayoutX() + zombie.getDeltaX());
// 	}
// }
