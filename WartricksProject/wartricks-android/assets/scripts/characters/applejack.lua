function create(entityfactory, world, layer)
        entityfactory:createEnemy(world, "apple", layer, math.random(0, 1080), 576 + 30, 0, -40):addToWorld()
end