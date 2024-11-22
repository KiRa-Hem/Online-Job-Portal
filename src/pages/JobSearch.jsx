import { useState } from 'react';
import { motion } from 'framer-motion';
import { Search, MapPin, Building, Filter } from 'lucide-react';

export default function JobSearch() {
  const [searchTerm, setSearchTerm] = useState('');
  const [location, setLocation] = useState('');

  const jobs = [
    {
      id: 1,
      title: 'Senior Frontend Developer',
      company: 'Tech Solutions Inc',
      location: 'San Francisco, CA',
      type: 'Full-time',
      salary: '$120k - $150k',
      description: 'We are looking for an experienced Frontend Developer to join our team...',
    },
    {
      id: 2,
      title: 'Full Stack Engineer',
      company: 'Innovation Labs',
      location: 'Remote',
      type: 'Full-time',
      salary: '$100k - $130k',
      description: 'Join our dynamic team building next-generation web applications...',
    },
  ];

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ duration: 0.5 }}
        >
          {/* Search Section */}
          <div className="bg-white rounded-lg shadow-md p-6 mb-8">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="relative">
                <Search className="absolute left-3 top-3 text-gray-400" />
                <input
                  type="text"
                  placeholder="Job title or keyword"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                />
              </div>
              <div className="relative">
                <MapPin className="absolute left-3 top-3 text-gray-400" />
                <input
                  type="text"
                  placeholder="Location"
                  value={location}
                  onChange={(e) => setLocation(e.target.value)}
                  className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                />
              </div>
            </div>
          </div>

          {/* Filters and Results */}
          <div className="grid grid-cols-1 lg:grid-cols-4 gap-8">
            {/* Filters */}
            <div className="lg:col-span-1">
              <div className="bg-white rounded-lg shadow-md p-6">
                <div className="flex items-center mb-4">
                  <Filter className="w-5 h-5 mr-2" />
                  <h2 className="text-lg font-semibold">Filters</h2>
                </div>
                {/* Add filter options here */}
              </div>
            </div>

            {/* Job Listings */}
            <div className="lg:col-span-3">
              <div className="space-y-6">
                {jobs.map((job) => (
                  <motion.div
                    key={job.id}
                    initial={{ x: -20, opacity: 0 }}
                    animate={{ x: 0, opacity: 1 }}
                    className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow"
                  >
                    <div className="flex justify-between items-start mb-4">
                      <div>
                        <h3 className="text-xl font-semibold text-blue-900">{job.title}</h3>
                        <div className="flex items-center text-gray-600 mt-1">
                          <Building className="w-4 h-4 mr-1" />
                          <span>{job.company}</span>
                          <MapPin className="w-4 h-4 ml-4 mr-1" />
                          <span>{job.location}</span>
                        </div>
                      </div>
                      <span className="px-3 py-1 bg-blue-100 text-blue-800 rounded-full text-sm">
                        {job.type}
                      </span>
                    </div>
                    <p className="text-gray-600 mb-4">{job.description}</p>
                    <div className="flex justify-between items-center">
                      <span className="text-blue-600 font-semibold">{job.salary}</span>
                      <button className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                        Apply Now
                      </button>
                    </div>
                  </motion.div>
                ))}
              </div>
            </div>
          </div>
        </motion.div>
      </div>
    </div>
  );
}