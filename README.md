# Pipelined Architectures for Latency Aware Caching With Delayed Hits
##### Simulator in caffeine library

## Abstract 
Modern computer systems use caching to speed up access to data and make better use of resources. These systems, especially in storage and cloud environments, are diverse, with different types of storage, locations, and caches, leading to inconsistent access times. Applications running on these systems also have varying access patterns, like how often they access data or if they do so in bursts. To handle this complexity, cache systems use complex algorithms that aim to balance conflicting priorities. However, these algorithms are hard to expand, debug, and integrate into software.

Our research introduces a flexible architecture that represents complex algorithms as a series of simple policies. This architecture automatically adjusts the different stages of the policy pipeline to maximize the system's performance for the current workload. This means we can add new strategies while preserving the old ones and only use them when they provide value. We've also developed a new cache algorithm called Burst Cache, which can identify and keep items that come in bursts for a long time, even if they aren't recent or frequent. This is particularly useful for handling brief bursts of data that arrive faster than the cache can process them, leading to delays and increased request times. Integrating this strategy into a cache system without harming performance for non-bursty data is a challenge. Our proposed algorithm can select the best strategy based on the workload's characteristics.

## Introduction 
Caching is a crucial technique to speed up data retrieval by taking advantage of variations in access times among different types of memory and storage. Caches store a small portion of data in a faster storage medium. For instance, DRAM can act as a cache for SSDs, and SSDs can serve as a cache for HDDs. When you access data stored in a cache, it's called a cache hit, while accessing data not in the cache is a cache miss. Cache hits are generally faster because the data is fetched from a quicker storage medium.

Traditionally, cache algorithms aimed to maximize the hit ratio, which is the percentage of accesses served faster by the cache. This works well because many workloads exhibit patterns like recency (recently accessed items are likely to be accessed again) and frequency (items accessed frequently over time are likely to be accessed again). However, in practice, it's challenging to predict which heuristic will be most beneficial. Therefore, adaptive caches often blend frequency and recency and dynamically allocate resources to each heuristic based on the workload's characteristics.


## Important 
* DRAM, which stands for Dynamic Random-Access Memory, is a type of volatile computer memory that is commonly used in computers and other electronic devices. It is a form of primary or main memory in a computer system and is responsible for temporarily storing data that     the CPU (Central Processing Unit) actively uses during program execution. Regarding the statement "DRAM can be a cache for SSD," this means that DRAM memory can be used as a cache to enhance the performance of Solid-State Drives (SSDs). SSDs are a type of non-volatile storage that is faster than traditional hard disk drives (HDDs) but still not as fast as DRAM. By using DRAM as a cache for frequently accessed data from the SSD, it helps reduce the latency of data access and improves overall system performance. This caching mechanism allows frequently used data to be quickly retrieved from the high-speed DRAM memory, while less frequently accessed data is stored on the slower but higher-capacity SSD. It's a technique used to bridge the speed gap between DRAM and SSD, optimizing data access in a computer system.
* 
