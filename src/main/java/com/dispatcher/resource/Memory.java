package main.java.com.dispatcher.resource;

import main.java.com.dispatcher.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Memory {

    static class MemoryBlock {
        int startAddress = 0;
        int endAddress = 960;
        boolean isOccupied;

        MemoryBlock(int startAddress, int endAddress) {
            this.startAddress = startAddress;
            this.endAddress = endAddress;
            this.isOccupied = false;
        }

        MemoryBlock(boolean isOccupied) {
            this.isOccupied = isOccupied;
        }
    }

    private List<MemoryBlock> memoryBlocks = new ArrayList<>();

    public Memory(int size) {
        memoryBlocks.add(new MemoryBlock(0, size - 1));
    }

    public boolean allocateMemory(Process process) {
        int processSize = process.getMemorySize();

        for (MemoryBlock block : memoryBlocks) {
            if (!block.isOccupied && block.endAddress - block.startAddress + 1 >= processSize) {
                block.isOccupied = true;
                if (block.endAddress - block.startAddress + 1 > processSize) {
                    memoryBlocks.add(new MemoryBlock(block.startAddress + processSize, block.endAddress));
                    block.endAddress = block.startAddress + processSize - 1;
                }
                process.setMemoryStartAddr(block.startAddress);
                return true;
            }
        }
        return false;
    }

    public void deallocateMemory(int startAddress) {
        for (int i = 0; i < memoryBlocks.size(); i++) {
            MemoryBlock block = memoryBlocks.get(i);
            MemoryBlock nextBlock = tryGetMemoryBlock(i + 1);
            MemoryBlock prevBlock = tryGetMemoryBlock(i - 1);

            if (block.startAddress == startAddress) {
                merge(block, nextBlock, prevBlock);
                memoryBlocks.remove(i);
                break;
            }
        }
    }

    private MemoryBlock tryGetMemoryBlock(int index) {
        try {
            return memoryBlocks.get(index);
        } catch (Exception e) {
            return new MemoryBlock(true); // null pointer exception engellemek icin null yerine block donup default olarak isOccupied true döndürüyoruz ki merge fonksiyonunda işlem yapılmasın.
        }
    }

    private void merge(MemoryBlock block, MemoryBlock nextBlock, MemoryBlock prevBlock) {
        if (!block.isOccupied && !nextBlock.isOccupied && !prevBlock.isOccupied) { // hem next hem de prev ile birlesecek
            prevBlock.endAddress = nextBlock.endAddress;
            memoryBlocks.remove(nextBlock);
        } else if (!block.isOccupied && !nextBlock.isOccupied) { // next ile birlesecek
            nextBlock.startAddress = block.startAddress;
        } else if (!block.isOccupied && !prevBlock.isOccupied) { // prev ile birlesecek
            prevBlock.endAddress = block.endAddress;
        }
    }

    public void displayMemoryStatus() {
        for (MemoryBlock block : memoryBlocks) {
            System.out.println("Start Address: " + block.startAddress + ", End Address: " + block.endAddress + ", Occupied: " + block.isOccupied);
        }
    }

}