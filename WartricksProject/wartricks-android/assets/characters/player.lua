function create(entityfactory, world)
        if os.getenv('DEBUG_MODE') then require "debugger"() end
        entityfactory:createPlayer(world, 500, 500):addToWorld()
end