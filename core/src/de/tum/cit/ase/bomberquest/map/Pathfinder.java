package de.tum.cit.ase.bomberquest.map;

import java.util.*;

public class Pathfinder {
    public static List<Tile> findPath(Tile start, Tile target, GameMap map) {
        Queue<Tile> queue = new LinkedList<>();
        Set<Tile> visited = new HashSet<>();
        Map<Tile, Tile> parentMap = new HashMap<>();

        queue.offer(start);
        visited.add(start);
        parentMap.put(start, null);

        while (!queue.isEmpty()) {
            Tile currentTile = queue.poll();

            if (currentTile.equals(target)) {
                return reconstructPath(parentMap, target);
            }

            int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
            for (int[] dir : directions) {
                int newX = currentTile.x() + dir[0];
                int newY = currentTile.y() + dir[1];
                Tile neighbor = new Tile(newX, newY);

                if (newX >= 0 && newX < map.getMAX_X() && newY >= 0 && newY < map.getMAX_Y() &&
                        map.isCellFree(newX, newY) && !visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, currentTile);
                }
            }
        }
        return null; // No path found
    }

    private static List<Tile> reconstructPath(Map<Tile, Tile> parentMap, Tile target) {
        List<Tile> path = new ArrayList<>();
        Tile current = target;
        while (current != null) {
            path.add(0, current);
            current = parentMap.get(current);
        }
        return path;
    }
}

