import { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { BriefcaseIcon, Users, CheckCircle, XCircle, Bell } from 'lucide-react';
import axios from 'axios';
import toast from 'react-hot-toast';

export default function AdminDashboard() {
  const [postedJobs, setPostedJobs] = useState([
    {
      id: 1,
      title: 'Senior Frontend Developer',
      applicants: 12,
      selected: 2,
      rejected: 5,
      pending: 5,
      datePosted: '2024-03-10'
    }
  ]);

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ duration: 0.5 }}
        >
          <div className="flex justify-between items-center mb-8">
            <h1 className="text-3xl font-bold text-gray-900">Admin Dashboard</h1>
            <button
              onClick={() => window.location.href = '/admin/post-job'}
              className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
            >
              Post New Job
            </button>
          </div>

          {/* Stats Overview */}
          <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
            <div className="bg-white rounded-lg shadow-md p-6">
              <div className="flex items-center">
                <BriefcaseIcon className="h-8 w-8 text-blue-600 mr-3" />
                <div>
                  <p className="text-gray-600">Posted Jobs</p>
                  <p className="text-2xl font-bold">{postedJobs.length}</p>
                </div>
              </div>
            </div>
            <div className="bg-white rounded-lg shadow-md p-6">
              <div className="flex items-center">
                <Users className="h-8 w-8 text-green-600 mr-3" />
                <div>
                  <p className="text-gray-600">Total Applicants</p>
                  <p className="text-2xl font-bold">45</p>
                </div>
              </div>
            </div>
            <div className="bg-white rounded-lg shadow-md p-6">
              <div className="flex items-center">
                <CheckCircle className="h-8 w-8 text-emerald-600 mr-3" />
                <div>
                  <p className="text-gray-600">Selected</p>
                  <p className="text-2xl font-bold">12</p>
                </div>
              </div>
            </div>
            <div className="bg-white rounded-lg shadow-md p-6">
              <div className="flex items-center">
                <Bell className="h-8 w-8 text-yellow-600 mr-3" />
                <div>
                  <p className="text-gray-600">Pending Review</p>
                  <p className="text-2xl font-bold">23</p>
                </div>
              </div>
            </div>
          </div>

          {/* Posted Jobs List */}
          <div className="bg-white rounded-lg shadow-md">
            <div className="p-6">
              <h2 className="text-xl font-semibold mb-4">Posted Jobs</h2>
              <div className="overflow-x-auto">
                <table className="min-w-full">
                  <thead className="bg-gray-50">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Job Title
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Applicants
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Status
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Date Posted
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Actions
                      </th>
                    </tr>
                  </thead>
                  <tbody className="bg-white divide-y divide-gray-200">
                    {postedJobs.map((job) => (
                      <tr key={job.id}>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm font-medium text-gray-900">{job.title}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm text-gray-900">{job.applicants}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex space-x-2 text-sm">
                            <span className="text-green-600">{job.selected} selected</span>
                            <span className="text-red-600">{job.rejected} rejected</span>
                            <span className="text-yellow-600">{job.pending} pending</span>
                          </div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm text-gray-900">{job.datePosted}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm">
                          <button
                            onClick={() => window.location.href = `/admin/jobs/${job.id}/applications`}
                            className="text-blue-600 hover:text-blue-900 mr-4"
                          >
                            View Applications
                          </button>
                          <button
                            onClick={() => window.location.href = `/admin/jobs/${job.id}/edit`}
                            className="text-gray-600 hover:text-gray-900"
                          >
                            Edit
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </motion.div>
      </div>
    </div>
  );
}