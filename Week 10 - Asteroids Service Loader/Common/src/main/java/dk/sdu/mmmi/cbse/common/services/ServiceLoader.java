/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;

/**
 *
 * @author Stephanie
 */
public class ServiceLoader {

    public static Collection<? extends IGamePluginService> getPluginServices() {
        return SPILocator.locateAll(IGamePluginService.class);
    }

    public static Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }

    public static Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return SPILocator.locateAll(IPostEntityProcessingService.class);
    }

    public static void LookupPlugins(GameData gameData, World world) {
        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService Plugin : ServiceLoader.getPluginServices()) {
            Plugin.start(gameData, world);
        }
    }

    public static void UpdatePlugins(GameData gameData, World world) {
        // Update
        for (IEntityProcessingService Service : ServiceLoader.getEntityProcessingServices()) {
            Service.process(gameData, world);
        }
    }

    public static void UpdatePostPlugins(GameData gameData, World world) {
        // Update
        for (IPostEntityProcessingService Service : ServiceLoader.getPostEntityProcessingServices()) {
            Service.process(gameData, world);
        }
    }
}

class SPILocator {

    @SuppressWarnings("rawtypes")
    private static final Map<Class, java.util.ServiceLoader> loadermap = new HashMap<Class, java.util.ServiceLoader>();

    private SPILocator() {
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> locateAll(Class<T> service) {
        java.util.ServiceLoader<T> loader = loadermap.get(service);

        boolean printStatement = false;

        if (loader == null) {
            loader = java.util.ServiceLoader.load(service);
            loadermap.put(service, loader);
            printStatement = true;
        }

        List<T> list = new ArrayList<T>();

        if (loader != null) {
            try {
                for (T instance : loader) {
                    list.add(instance);
                }
            } catch (ServiceConfigurationError serviceError) {
                serviceError.printStackTrace();
            }
        }

        if (printStatement) {
            System.out.println("Found " + list.size() + " implementations for interface: " + service.getName());
        }

        return list;
    }
}
